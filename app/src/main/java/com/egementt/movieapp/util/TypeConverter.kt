package com.egementt.movieapp.util

import androidx.room.TypeConverter

class TypeConverter {

    @TypeConverter
    fun fromGenre(genres: List<Int>): String {
        return genres.toString().removePrefix("[").removeSuffix("]")
    }

    @TypeConverter
    fun toGenre(genres: String): List<Int>{
        val list: MutableList<Int> = mutableListOf()
        genres.split(",").map {
            list.add(it.trim().toInt())
        }
        return list
    }
}