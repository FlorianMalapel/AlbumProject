package com.flo.albumproject.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackResponse(
    @Json(name = "id")
    val id: Int,

    @Json(name = "albumId")
    val albumId: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: String,

    @Json(name = "thumbnailUrl")
    val thumbnailUrl: String,

)