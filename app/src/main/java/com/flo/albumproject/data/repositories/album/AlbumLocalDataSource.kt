package com.flo.albumproject.data.repositories.album

import com.flo.albumproject.data.db.dao.AlbumDao
import com.flo.albumproject.data.mapper.AlbumEntityMapper
import com.flo.albumproject.domain.entities.Album
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AlbumLocalDataSource {
    suspend fun getAll(): Flow<List<Album>>
    suspend fun save(album: Album)
    suspend fun saveAll(albums: List<Album>)
}

class AlbumLocalDataSourceImpl(
    private val albumDao: AlbumDao,
    private val albumMapper: AlbumEntityMapper
): AlbumLocalDataSource {

    override suspend fun getAll(): Flow<List<Album>> {
        return albumDao.getAll().map { albumEntities ->
            albumEntities.map { entity ->
                albumMapper.toAlbum(entity = entity)
            }
        }
    }

    override suspend fun save(album: Album) {
        albumDao.insert(albumMapper.toAlbumEntity(album))
    }

    override suspend fun saveAll(albums: List<Album>) {
        albumDao.insertAll(
            *albums.map { album ->
                albumMapper.toAlbumEntity(album)
            }.toTypedArray()
        )
    }
}