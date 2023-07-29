package com.flo.albumproject.domain.entities

data class Album(
    val id: Int,
    val tracks: MutableList<Track>
)
