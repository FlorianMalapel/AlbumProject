package com.flo.albumproject

import android.graphics.Color
import com.flo.albumproject.data.entities.AlbumEntity
import com.flo.albumproject.data.mapper.AlbumEntityMapper
import com.flo.albumproject.domain.entities.Album
import org.junit.Assert
import org.junit.Test

class AlbumEntityMapperTest {

    private val mapper = AlbumEntityMapper()

    private val entity = AlbumEntity(
        id = 43,
        color = Color.BLUE
    )

    private val album = Album(
        id = 423,
        color = Color.BLACK
    )

    @Test
    fun TestAlbumToAlbumEntity() {
        val albumEntityFromAlbum = mapper.toAlbumEntity(album)
        Assert.assertEquals(
            "AlbumEntity id is not the same than Album id",
            albumEntityFromAlbum.id, album.id
        )
        Assert.assertEquals(
            "AlbumEntity color is not the same than Album color",
            albumEntityFromAlbum.color, album.color
        )
    }

    @Test
    fun TestAlbumEntityToAlbum() {
        val albumFromAlbumEntity = mapper.toAlbum(entity)
        Assert.assertEquals(
            "Album id is not the same than AlbumEntity id",
            albumFromAlbumEntity.id, entity.id
        )
        Assert.assertEquals(
            "Album color is not the same than AlbumEntity color",
            albumFromAlbumEntity.color, entity.color
        )
    }

}