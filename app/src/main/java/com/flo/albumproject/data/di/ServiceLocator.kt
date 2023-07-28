package com.flo.albumproject.data.di

import android.content.Context
import com.flo.albumproject.data.api.NetworkModule
import com.flo.albumproject.data.db.ProjectDatabase

object ServiceLocator {

    private var database: ProjectDatabase? = null

    private val networkModule by lazy {
        NetworkModule()
    }

    /**
     * database related
     */
    private fun createDatabase(context: Context): ProjectDatabase {
        val result = ProjectDatabase.getDatabase(context)
        this.database = result
        return result
    }

}