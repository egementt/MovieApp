package com.egementt.movieapp.data.remote

import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.data.model.MovieResponse
import com.egementt.movieapp.presentation.MovieResponseState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = "6c32b71ebd7e8cd9f8bc99bd77718306",
        @Query("language") language: String = "en-US",
        @Query("page") page: Int? = 1,
    ): MovieResponse
}