package com.flo.albumproject.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

sealed class NetworkStatus {
    object Online : NetworkStatus()
    object Offline : NetworkStatus()
}

class NetworkStatusTracker(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var status: MutableLiveData<NetworkStatus> = MutableLiveData(NetworkStatus.Online)

    init {
        setup()
    }

    private fun setup() {
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                GlobalScope.launch(Dispatchers.IO) {
                    this@NetworkStatusTracker.status.postValue(NetworkStatus.Offline)
                }
            }

            override fun onAvailable(network: Network) {
                this@NetworkStatusTracker.status.postValue(NetworkStatus.Online)
            }

            override fun onLost(network: Network) {
                GlobalScope.launch(Dispatchers.IO) {
                    this@NetworkStatusTracker.status.postValue(NetworkStatus.Offline)
                }
            }

        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkStatusCallback)
    }
}