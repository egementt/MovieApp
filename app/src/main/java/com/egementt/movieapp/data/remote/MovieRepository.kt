package com.egementt.movieapp.data.remote

import com.egementt.movieapp.data.model.CreditsModel
import com.egementt.movieapp.data.model.MovieResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: MovieApiService) {

    suspend fun getPopularMovies(): MovieResponse = apiService.getPopularMovies()

    suspend fun getUpcomingMovies(): MovieResponse = apiService.getUpcomingMovies()

    suspend fun getMovieCredits(movieId: String): CreditsModel = apiService.getMovieCredits(movieId)

    suspend fun getRecommendedMovies(movieId: String): MovieResponse = apiService.getRecommendedMovies(movieId)
}