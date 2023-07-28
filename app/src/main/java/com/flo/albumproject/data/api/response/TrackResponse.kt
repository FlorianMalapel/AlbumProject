package com.flo.albumproject.data.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackResponse(
    @Json(name = "id")
    val id: Int,

    @Json(name = "album_id")
    val albumId: Int,

    @Json(name = "title")
    val title: String,

    @Json(name = "url")
    val url: Int,

    @Json(name = "thumbnail_url")
    val thumbnailUrl: Int,

)