package com.flo.albumproject.domain.usecases

import android.util.Log
import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.domain.repositories.TrackRepository
import kotlinx.coroutines.flow.Flow

class TrackUseCase(private val trackRepository: TrackRepository) {

    suspend fun getRemote(): NetworkResult {
        try {
            val result = trackRepository.getRemote()
            if (result is NetworkResult.Success) {
                result.tracks?.let { tracks ->
                    trackRepository.save(tracks)
                }
            }
            return result
        } catch (e: Exception) {
            Log.e("#http", "Something went wrong while getting remote tracks", e)
            return NetworkResult.Error
        }
    }

    suspend fun getLocal(): Flow<List<Track>> = trackRepository.getLocal()
}