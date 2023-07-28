package com.flo.albumproject.utils.network

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

object NetworkStatusListener {

    private lateinit var networkTracker: NetworkStatusTracker

    fun setup(context: Context) {
        networkTracker = NetworkStatusTracker(context)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<NetworkStatus>) {
        networkTracker.status.observe(owner, observer)
    }

    fun setStatus(status: NetworkStatus) {
        networkTracker.status.apply {
            if (value != status) {
                postValue(status)
            }
        }
    }

    fun getStatus(): NetworkStatus = networkTracker.status.value ?: NetworkStatus.Online

    fun isOnline(): Boolean = networkTracker.status.value == NetworkStatus.Online

    fun isOffline(): Boolean = networkTracker.status.value != NetworkStatus.Online


}