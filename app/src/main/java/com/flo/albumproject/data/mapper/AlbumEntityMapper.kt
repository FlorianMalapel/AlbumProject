package com.flo.albumproject.data.mapper

import com.flo.albumproject.data.entities.AlbumEntity
import com.flo.albumproject.data.entities.TrackEntity
import com.flo.albumproject.domain.entities.Album

class AlbumEntityMapper {

    fun toAlbumEntity(album: Album): AlbumEntity {
        return AlbumEntity(
            id = album.id,
            color = album.color
        )
    }

    fun toAlbum(entity: AlbumEntity): Album {
        return Album(
            id = entity.id,
            color = entity.color
        )
    }

}