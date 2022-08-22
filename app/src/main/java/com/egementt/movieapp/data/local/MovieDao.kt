package com.egementt.movieapp.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.egementt.movieapp.data.model.Movie
import retrofit2.http.GET

interface MovieDao {


    @GET("SELECT * FROM movie")
    fun getFavoriteMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)
}