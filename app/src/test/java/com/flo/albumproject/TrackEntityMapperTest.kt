package com.flo.albumproject

import com.flo.albumproject.data.api.response.TrackResponse
import com.flo.albumproject.data.entities.TrackEntity
import com.flo.albumproject.data.mapper.TrackEntityMapper
import com.flo.albumproject.domain.entities.Track
import org.junit.Test

import org.junit.Assert.*

class TrackEntityMapperTest {

    private val mapper = TrackEntityMapper()

    private val response = TrackResponse(
        id = 12,
        albumId = 2,
        url = "https://via.placeholder.codm/600/92c952",
        thumbnailUrl = "https://via.placeholder.codm/150/92c952",
        title = "accusamus beatae ad facilis cum similique qui sunt",
    )

    private val entity = TrackEntity(
        id = 121,
        albumId = 12,
        url = "https://via.placeholder.com/600/92c952",
        thumbnailUrl = "https://via.placeholder.com/150/92c952",
        title = "accusamus beeeatae ad facilis cum similique qui sunt",
    )

    private val track = Track(
        id = 1112,
        albumId = 234,
        url = "https://via.placeDzeholder.coam/600/92c952",
        thumbnailUrl = "https://via.placgteholder.coam/150/92c952",
        title = "accusamus beatae afred facilis cum similique qui sunt",
    )

    @Test
    fun TestTrackResponseToEntity() {
        val entityFromResponse = mapper.toTrackEntity(response)
        assertEquals(
            "Entity id is not the same than response id",
            entityFromResponse.id, response.id
        )
        assertEquals(
            "Entity albumId is not the same than response albumId",
            entityFromResponse.albumId, response.albumId
        )
        assertEquals(
            "Entity url is not the same than response url",
            entityFromResponse.url, response.url
        )
        assertEquals(
            "Entity thumbnailUrl is not the same than response thumbnailUrl",
            entityFromResponse.thumbnailUrl, response.thumbnailUrl
        )
        assertEquals(
            "Entity title is not the same than response title",
            entityFromResponse.title, response.title
        )
    }

    @Test
    fun TestTrackResponseToTrack() {
        val trackFromResponse = mapper.toTrack(response)
        assertEquals(
            "Track id is not the same than response id",
            trackFromResponse.id, response.id
        )
        assertEquals(
            "Track albumId is not the same than response albumId",
            trackFromResponse.albumId, response.albumId
        )
        assertEquals(
            "Track url is not the same than response url",
            trackFromResponse.url, response.url
        )
        assertEquals(
            "Track thumbnailUrl is not the same than response thumbnailUrl",
            trackFromResponse.thumbnailUrl, response.thumbnailUrl
        )
        assertEquals(
            "Track title is not the same than response title",
            trackFromResponse.title, response.title
        )
    }

    @Test
    fun TestTrackToTrackEntity() {
        val trackEntityFromTrack = mapper.toTrackEntity(track)
        assertEquals(
            "TrackEntity id is not the same than Track id",
            trackEntityFromTrack.id, track.id
        )
        assertEquals(
            "TrackEntity albumId is not the same than Track albumId",
            trackEntityFromTrack.albumId, track.albumId
        )
        assertEquals(
            "TrackEntity url is not the same than Track url",
            trackEntityFromTrack.url, track.url
        )
        assertEquals(
            "TrackEntity thumbnailUrl is not the same than Track thumbnailUrl",
            trackEntityFromTrack.thumbnailUrl, track.thumbnailUrl
        )
        assertEquals(
            "TrackEntity title is not the same than Track title",
            trackEntityFromTrack.title, track.title
        )
    }

    @Test
    fun TestTrackEntityToTrack() {
        val trackFromTrackEntity = mapper.toTrack(entity)
        assertEquals(
            "Track id is not the same than TrackEntity id",
            trackFromTrackEntity.id, entity.id
        )
        assertEquals(
            "Track albumId is not the same than TrackEntity albumId",
            trackFromTrackEntity.albumId, entity.albumId
        )
        assertEquals(
            "Track url is not the same than TrackEntity url",
            trackFromTrackEntity.url, entity.url
        )
        assertEquals(
            "Track thumbnailUrl is not the same than TrackEntity thumbnailUrl",
            trackFromTrackEntity.thumbnailUrl, entity.thumbnailUrl
        )
        assertEquals(
            "Track title is not the same than TrackEntity title",
            trackFromTrackEntity.title, entity.title
        )
    }
}