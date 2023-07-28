package com.flo.albumproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flo.albumproject.data.db.dao.TrackDao
import com.flo.albumproject.data.entities.TrackEntities

@Database(entities = [TrackEntities::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProjectDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao

    companion object {
        @Volatile
        private var INSTANCE: ProjectDatabase? = null

        fun getDatabase(appContext: Context): ProjectDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(appContext, ProjectDatabase::class.java, ProjectDatabase::class.simpleName!!)
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}