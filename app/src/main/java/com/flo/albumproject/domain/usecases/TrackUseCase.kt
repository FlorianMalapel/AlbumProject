package com.flo.albumproject.domain.usecases

import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.domain.repositories.TrackRepository
import kotlinx.coroutines.flow.Flow

class TrackUseCase(private val trackRepository: TrackRepository) {

    suspend fun getRemote(): NetworkResult {
        val result = trackRepository.getRemote()
        if (result is NetworkResult.Success) {
            result.tracks?.let { tracks ->
                trackRepository.save(tracks)
            }
        }
        return result
    }

    suspend fun getLocal(): Flow<List<Track>> = trackRepository.getLocal()
}