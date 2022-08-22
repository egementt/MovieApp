package com.egementt.movieapp.presentation.state

import com.egementt.movieapp.data.model.Cast

sealed class CastState {
    data class Success(val casts: List<Cast>) : CastState()
    data class Error(val string: String) : CastState()
    object Loading : CastState()
}