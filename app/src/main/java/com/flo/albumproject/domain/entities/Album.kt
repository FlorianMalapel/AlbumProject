package com.flo.albumproject.domain.entities

data class Album(
    val id: Int,
    val color: Int,
    val tracks: MutableList<Track>? = null
)
