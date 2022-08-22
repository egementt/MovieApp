package com.egementt.movieapp.presentation.state

import com.egementt.movieapp.data.model.Movie

sealed class BookmarkMovieState {
    object Unmarked : BookmarkMovieState()
    object Success: BookmarkMovieState()
    data class Error(val error: String) : BookmarkMovieState()
}