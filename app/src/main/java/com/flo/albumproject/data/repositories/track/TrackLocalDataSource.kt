package com.flo.albumproject.data.repositories.track

import com.flo.albumproject.data.db.dao.TrackDao
import com.flo.albumproject.data.mapper.TrackEntityMapper
import com.flo.albumproject.domain.entities.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface  TrackLocalDataSource {
    suspend fun getAll(): Flow<List<Track>>
    suspend fun saveAll(tracks: List<Track>)
    suspend fun getByAlbumId(albumId: Int): Flow<List<Track>>
}

class TrackLocalDataSourceImpl(
    private val trackDao: TrackDao,
    private val trackMapper: TrackEntityMapper
) : TrackLocalDataSource {

    override suspend fun getAll(): Flow<List<Track>> {
        return trackDao.getAll().map { trackEntities ->
            trackEntities.map { entity ->
                trackMapper.toTrack(entity = entity)
            }
        }
    }

    override suspend fun saveAll(tracks: List<Track>) {
        trackDao.insertAll(
            *tracks.map { track ->
                trackMapper.toTrackEntity(track)
            }.toTypedArray()
        )
    }

    override suspend fun getByAlbumId(albumId: Int): Flow<List<Track>> {
        return trackDao.getAllWithAlbumId(albumId).map { trackEntities ->
            trackEntities.map { entity ->
                trackMapper.toTrack(entity = entity)
            }
        }
    }
}