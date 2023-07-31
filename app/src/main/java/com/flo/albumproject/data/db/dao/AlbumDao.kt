package com.flo.albumproject.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flo.albumproject.data.entities.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(track: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg tracks: AlbumEntity)

    @Query("SELECT * FROM album")
    fun getAll(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM album WHERE id = :id")
    fun get(id: Int): Flow<List<AlbumEntity>>

    @Delete
    suspend fun delete(album: AlbumEntity)

}