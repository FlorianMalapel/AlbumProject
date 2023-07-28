package com.flo.albumproject.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flo.albumproject.data.entities.TrackEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntities)

    @Insert
    fun insertAll(vararg tracks: TrackEntities)

    @Query("SELECT * FROM track")
    fun getAll(): Flow<List<TrackEntities>>

    @Query("SELECT * FROM track WHERE album_id = :albumId")
    fun getAllWithAlbumId(albumId: Int): Flow<List<TrackEntities>>

    @Delete
    suspend fun delete(track: TrackEntities)
}