package com.flo.albumproject.domain.usecases

import com.flo.albumproject.domain.entities.Track


sealed class NetworkResult {
    data class Success(
        val albums: List<Track>?
    ) : NetworkResult()

    object Error : NetworkResult()
}