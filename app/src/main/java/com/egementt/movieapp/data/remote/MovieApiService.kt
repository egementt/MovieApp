package com.egementt.movieapp.data.remote

import com.egementt.movieapp.data.model.CreditsModel
import com.egementt.movieapp.data.model.MovieResponse
import com.egementt.movieapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int? = 1,
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int? = 1,
    ): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = API_KEY
    ): CreditsModel

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendedMovies(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = API_KEY
    ):MovieResponse
}
