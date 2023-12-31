package com.flo.albumproject.data.di

import android.content.Context
import com.flo.albumproject.data.api.NetworkModule
import com.flo.albumproject.data.db.ProjectDatabase
import com.flo.albumproject.data.mapper.AlbumEntityMapper
import com.flo.albumproject.data.mapper.TrackEntityMapper
import com.flo.albumproject.data.repositories.album.AlbumLocalDataSource
import com.flo.albumproject.data.repositories.album.AlbumLocalDataSourceImpl
import com.flo.albumproject.data.repositories.album.AlbumRepositoryImpl
import com.flo.albumproject.data.repositories.track.TrackLocalDataSource
import com.flo.albumproject.data.repositories.track.TrackLocalDataSourceImpl
import com.flo.albumproject.data.repositories.track.TrackRemoteDataSource
import com.flo.albumproject.data.repositories.track.TrackRemoteDataSourceImpl
import com.flo.albumproject.data.repositories.track.TrackRepositoryImpl
import com.flo.albumproject.domain.repositories.AlbumRepository
import com.flo.albumproject.domain.repositories.TrackRepository

object ServiceLocator {

    private var database: ProjectDatabase? = null

    private val networkModule by lazy {
        NetworkModule()
    }

    private val trackEntityMapper: TrackEntityMapper by lazy {
        TrackEntityMapper()
    }

    private val albumEntityMapper: AlbumEntityMapper by lazy {
        AlbumEntityMapper()
    }

    @Volatile
    var trackRepository: TrackRepository? = null

    @Volatile
    var albumRepository: AlbumRepository? = null

    /**
     * database related
     */
    private fun createDatabase(context: Context): ProjectDatabase {
        val result = ProjectDatabase.getDatabase(context)
        this.database = result
        return result
    }


    /**
     * track repository related
     */
    fun provideTrackRepository(context: Context): TrackRepository {
        synchronized(this) {
            return trackRepository ?: createTrackRepository(context)
        }
    }


    private fun createTrackRepository(context: Context): TrackRepository {
        val newRepo = TrackRepositoryImpl(
            localDataSource = createTrackLocalDataSource(context),
            remoteDataSource = createTrackRemoteDataSource()
        )
        this.trackRepository = newRepo
        return newRepo
    }

    private fun createTrackLocalDataSource(context: Context): TrackLocalDataSource {
        val database = database ?: createDatabase(context)
        return TrackLocalDataSourceImpl(
            trackDao = database.trackDao(),
            trackMapper = trackEntityMapper
        )
    }

    private fun createTrackRemoteDataSource(): TrackRemoteDataSource {
        return TrackRemoteDataSourceImpl(
            technicalTestApi = networkModule.technicalTestApi,
            trackMapper = trackEntityMapper
        )
    }

    /**
     * album repository related
     */
    fun provideAlbumRepository(context: Context): AlbumRepository {
        synchronized(this) {
            return albumRepository ?: createAlbumRepository(context)
        }
    }


    private fun createAlbumRepository(context: Context): AlbumRepository {
        val newRepo = AlbumRepositoryImpl(
            localDataSource = createAlbumLocalDataSource(context),
        )
        this.albumRepository = newRepo
        return newRepo
    }

    private fun createAlbumLocalDataSource(context: Context): AlbumLocalDataSource {
        val database = database ?: createDatabase(context)
        return AlbumLocalDataSourceImpl(
            albumDao = database.albumDao(),
            albumMapper = albumEntityMapper
        )
    }
}