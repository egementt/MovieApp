package com.egementt.movieapp.presentation.state

import com.egementt.movieapp.data.model.MovieResponse

sealed class MovieResponseState {
    data class Success(val movieResponse: MovieResponse): MovieResponseState()
    data class Error(val error: String): MovieResponseState()
    object Loading : MovieResponseState()
}