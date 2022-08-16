package com.egementt.movieapp.data.remote

import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.data.model.MovieResponse
import com.egementt.movieapp.di.ApiModule
import com.egementt.movieapp.presentation.MovieResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: MovieApiService) {

    suspend fun getPopularMovies(): MovieResponse = apiService.getPopularMovies()

    suspend fun getUpcomingMovies(): MovieResponse = apiService.getUpcomingMovies()
}