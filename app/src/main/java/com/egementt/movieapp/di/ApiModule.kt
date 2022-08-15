package com.egementt.movieapp.di

import com.egementt.movieapp.data.remote.MovieApiService
import com.egementt.movieapp.data.remote.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    fun provideRetrofit(): MovieApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(MovieApiService::class.java)


    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: MovieApiService
    ): MovieRepository = MovieRepository(apiService)
}