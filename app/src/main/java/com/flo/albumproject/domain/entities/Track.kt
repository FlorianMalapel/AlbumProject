package com.flo.albumproject.domain.entities

data class Track(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)