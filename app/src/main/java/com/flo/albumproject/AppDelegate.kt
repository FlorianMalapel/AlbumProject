package com.flo.albumproject

import android.app.Application
import com.flo.albumproject.data.di.ServiceLocator
import com.flo.albumproject.domain.usecases.AlbumUseCase
import com.flo.albumproject.domain.usecases.TrackUseCase
import com.flo.albumproject.utils.network.NetworkStatusListener

class AppDelegate : Application() {

    val trackUseCase: TrackUseCase
        get() = TrackUseCase(
            trackRepository = ServiceLocator.provideTrackRepository(this),
            albumRepository = ServiceLocator.provideAlbumRepository(this),
        )

    val albumUseCase: AlbumUseCase
        get() = AlbumUseCase(ServiceLocator.provideAlbumRepository(this))

    override fun onCreate() {
        super.onCreate()
        NetworkStatusListener.setup(this)
    }

}