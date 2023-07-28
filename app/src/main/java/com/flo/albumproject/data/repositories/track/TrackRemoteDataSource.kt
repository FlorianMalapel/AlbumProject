package com.flo.albumproject.data.repositories.track

import com.flo.albumproject.data.api.TechnicalTestApi
import com.flo.albumproject.data.mapper.TrackEntityMapper
import com.flo.albumproject.domain.usecases.NetworkResult

interface TrackRemoteDataSource {
    suspend fun getAlbums(): NetworkResult
}

class TrackRemoteDataSourceImpl(
    val technicalTestApi: TechnicalTestApi,
    val trackMapper: TrackEntityMapper
) : TrackRemoteDataSource {

    override suspend fun getAlbums(): NetworkResult {
        val response = technicalTestApi.getAlbums()
        return if (response.isSuccessful) {
            NetworkResult.Success(
                response.body()?.map { trackResponse ->
                    trackMapper.toTrack(trackResponse)
                })
        } else {
            NetworkResult.Error
        }
    }
}