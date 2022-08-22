package com.egementt.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egementt.movieapp.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): MovieDao
}