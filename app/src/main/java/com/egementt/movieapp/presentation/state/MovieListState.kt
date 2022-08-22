package com.egementt.movieapp.presentation.state

import com.egementt.movieapp.data.model.Movie



    sealed class MovieListState {
        data class Success(val movies: List<Movie>) : MovieListState()
        data class Error(val error: String) : MovieListState()
        object Loading : MovieListState()
    }

