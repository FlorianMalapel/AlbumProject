package com.flo.albumproject.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flo.albumproject.data.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tracks: TrackEntity)

    @Query("SELECT * FROM track")
    fun getAll(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM track WHERE album_id = :albumId")
    fun getAllWithAlbumId(albumId: Int): Flow<List<TrackEntity>>

    @Delete
    suspend fun delete(track: TrackEntity)
}