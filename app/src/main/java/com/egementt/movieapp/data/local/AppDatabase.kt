package com.egementt.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.util.TypeConverter

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): MovieDao
}