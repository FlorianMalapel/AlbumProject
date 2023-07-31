package com.flo.albumproject.domain.repositories

import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.domain.usecases.NetworkResult
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

    suspend fun save(tracks: List<Track>)

    suspend fun getRemote(): NetworkResult

    suspend fun getLocal(): Flow<List<Track>>

    suspend fun getLocalByAlbumId(albumId: Int): Flow<List<Track>>

}