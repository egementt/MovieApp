package com.egementt.movieapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.local.LocalRepository
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.presentation.state.BookmarkMovieState
import com.egementt.movieapp.presentation.state.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: LocalRepository) :
    ViewModel() {

    private val _favoriteState: MutableStateFlow<MovieListState> =
        MutableStateFlow(MovieListState.Loading)
    val favoriteState get() = _favoriteState as StateFlow<MovieListState>




    fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteState.collect {
                try {
                    _favoriteState.value = MovieListState.Success(repository.getFavoriteMovies())
                } catch (e: Exception) {
                    _favoriteState.value =
                        MovieListState.Error(e.message ?: "An unknown error occurred")
                }
            }

        }
    }



}
