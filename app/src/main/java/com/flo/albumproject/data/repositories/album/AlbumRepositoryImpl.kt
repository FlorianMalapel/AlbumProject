package com.flo.albumproject.data.repositories.album

import com.flo.albumproject.domain.entities.Album
import com.flo.albumproject.domain.repositories.AlbumRepository
import kotlinx.coroutines.flow.Flow

class AlbumRepositoryImpl(
    private val localDataSource: AlbumLocalDataSource
): AlbumRepository {

    override suspend fun save(album: Album) = localDataSource.save(album)

    override suspend fun getLocal(): Flow<List<Album>> = localDataSource.getAll()

}