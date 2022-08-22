package com.egementt.movieapp.data.local

import com.egementt.movieapp.data.model.Movie
import javax.inject.Inject

class LocalRepository @Inject constructor(private val movieDao: MovieDao) {

    fun getFavoriteMovies() : List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    suspend fun insertMovie(movie: Movie){
        return movieDao.insertMovie(movie)
    }
}