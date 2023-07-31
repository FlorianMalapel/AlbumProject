package com.flo.albumproject.data.repositories.track

import com.flo.albumproject.domain.entities.Track
import com.flo.albumproject.domain.repositories.TrackRepository
import com.flo.albumproject.domain.usecases.NetworkResult
import kotlinx.coroutines.flow.Flow

class TrackRepositoryImpl(
    private val localDataSource: TrackLocalDataSource,
    private val remoteDataSource: TrackRemoteDataSource
) : TrackRepository {

    override suspend fun save(tracks: List<Track>) = localDataSource.saveAll(tracks)

    override suspend fun getRemote(): NetworkResult = remoteDataSource.getAlbums()

    override suspend fun getLocal(): Flow<List<Track>> = localDataSource.getAll()

    override suspend fun getLocalByAlbumId(albumId: Int) = localDataSource.getByAlbumId(albumId)
}