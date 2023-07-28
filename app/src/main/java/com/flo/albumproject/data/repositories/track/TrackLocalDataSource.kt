package com.flo.albumproject.data.repositories.track

import com.flo.albumproject.data.db.dao.TrackDao
import com.flo.albumproject.data.mapper.TrackEntityMapper
import com.flo.albumproject.domain.entities.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TrackLocalDataSource {
    fun getAll(): Flow<List<Track>>

    fun saveAll(tracks: List<Track>)
}

class TrackLocalDataSourceImpl(
    val trackDao: TrackDao,
    val trackMapper: TrackEntityMapper
) : TrackLocalDataSource {

    override fun getAll(): Flow<List<Track>> {
        return trackDao.getAll().map { trackEntities ->
            trackEntities.map { entity ->
                trackMapper.toTrack(entity = entity)
            }
        }
    }

    override fun saveAll(tracks: List<Track>) {
        trackDao.insertAll(
            *tracks.map { track ->
                trackMapper.toTrackEntity(track)
            }.toTypedArray()
        )
    }
}