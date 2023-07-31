package com.flo.albumproject.domain.repositories

import com.flo.albumproject.domain.entities.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun save(album: Album)
    suspend fun getLocal(): Flow<List<Album>>

}