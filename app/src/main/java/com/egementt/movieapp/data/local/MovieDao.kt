package com.egementt.movieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egementt.movieapp.data.model.Movie
import retrofit2.http.GET

@Dao
interface MovieDao {


    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)
}