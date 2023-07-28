package com.flo.albumproject.data.api

import com.flo.albumproject.data.api.response.TrackResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TechnicalTestApi {

    @GET("img/shared/technical-test.json")
    suspend fun getAlbums(): List<TrackResponse>

}