package com.flo.albumproject.data.db

import androidx.room.TypeConverter
import com.flo.albumproject.data.entities.TrackEntity

/**
 * Here we can find the converters for the database if there is any
 */
class Converters {
    @TypeConverter
    fun fromTrackIDList(trackIDs: List<String>?): String? {
        return trackIDs?.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToTrackIDList(trackIDs: String?): List<String>? {
        return trackIDs?.split(",")
    }
}