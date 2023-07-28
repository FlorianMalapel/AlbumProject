package com.flo.albumproject.data.mapper

import com.flo.albumproject.data.api.response.TrackResponse
import com.flo.albumproject.data.entities.TrackEntity
import com.flo.albumproject.domain.entities.Track

class TrackEntityMapper {

    fun toTrackEntity(response: TrackResponse): TrackEntity {
        return TrackEntity(
            id = response.id,
            albumId = response.albumId,
            title = response.title,
            url = response.url,
            thumbnailUrl = response.thumbnailUrl
        )
    }

    fun toTrackEntity(track: Track): TrackEntity {
        return TrackEntity(
            id = track.id,
            albumId = track.albumId,
            title = track.title,
            url = track.url,
            thumbnailUrl = track.thumbnailUrl
        )
    }

    fun toTrack(entity: TrackEntity): Track {
        return Track(
            id = entity.id,
            albumId = entity.albumId,
            title = entity.title,
            url = entity.url,
            thumbnailUrl = entity.thumbnailUrl
        )
    }

    fun toTrack(response: TrackResponse): Track {
        return Track(
            id = response.id,
            albumId = response.albumId,
            title = response.title,
            url = response.url,
            thumbnailUrl = response.thumbnailUrl
        )
    }

}