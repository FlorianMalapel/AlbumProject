package com.flo.albumproject

import android.app.Application
import com.flo.albumproject.data.di.ServiceLocator
import com.flo.albumproject.domain.usecases.TrackUseCase
import com.flo.albumproject.utils.network.NetworkStatusListener

class AppDelegate : Application() {

    val albumUseCase: TrackUseCase
        get() = TrackUseCase(ServiceLocator.provideTrackRepository(this))

    override fun onCreate() {
        super.onCreate()
        NetworkStatusListener.setup(this)
    }

}