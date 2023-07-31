package com.flo.albumproject.domain.usecases

import android.util.Log
import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.domain.repositories.AlbumRepository
import com.flo.albumproject.domain.repositories.TrackRepository
import kotlinx.coroutines.flow.Flow

class TrackUseCase(
    private val trackRepository: TrackRepository,
    private val albumRepository: AlbumRepository,
    ) {

    suspend fun getRemote(): NetworkResult {
        return try {
            val result = trackRepository.getRemote()
            if (result is NetworkResult.Success) {
                result.tracks?.let { tracks ->
                    trackRepository.save(tracks)
                    tracks.groupBy { it.albumId }.let { grouped ->
                        albumRepository.saveAll(grouped.map { Album(it.key) })
                    }
                }
            }
            result
        } catch (e: Exception) {
            Log.e("#http", "Something went wrong while getting remote tracks", e)
            NetworkResult.Error
        }
    }

    suspend fun getLocal(): Flow<List<Track>> = trackRepository.getLocal()

    suspend fun getLocalTracksByAlbumId(id: Int) = trackRepository.getLocalByAlbumId(id)
}