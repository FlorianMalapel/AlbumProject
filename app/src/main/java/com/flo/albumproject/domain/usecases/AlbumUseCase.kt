package com.flo.albumproject.domain.usecases

import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.domain.repositories.AlbumRepository
import kotlinx.coroutines.flow.Flow

class AlbumUseCase(
    private val albumRepository: AlbumRepository
) {

    suspend fun getLocal(): Flow<List<Album>> = albumRepository.getLocal()

}