package com.flo.albumproject.utils.network

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
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
    var status: MutableLiveData<NetworkStatus> = MutableLiveData()

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

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val isOnline =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                this@NetworkStatusTracker.status.postValue(if (isOnline) NetworkStatus.Online else NetworkStatus.Offline)
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, networkStatusCallback)
        status.postValue(
            if (isDeviceOnline()) {
                NetworkStatus.Online
            } else {
                NetworkStatus.Offline
            }
        )
    }

    private fun isDeviceOnline(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities != null
        } else {
            // below Marshmallow
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable
        }
    }
}