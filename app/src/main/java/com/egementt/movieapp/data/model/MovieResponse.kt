package com.egementt.movieapp.data.model

data class MovieResponse(
    val page: Int,
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)