package com.egementt.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.model.MovieResponse
import com.egementt.movieapp.data.remote.MovieRepository
import com.egementt.movieapp.di.ApiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository ) : ViewModel() {

    private val _movieListUiState : MutableStateFlow<MovieResponseState> = MutableStateFlow(MovieResponseState.Loading)
    val movieListUiState get() = _movieListUiState

    init {
        getPopularMovies()
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            _movieListUiState.collect{
                try {
                    _movieListUiState.value = MovieResponseState.Success(repository.getPopularMovies())
                }catch (e: Exception){
                    _movieListUiState.value = MovieResponseState.Error(e.message ?:"An error occurred")
                }
            }
        }
    }


}




sealed class MovieResponseState {
    data class Success(val movieResponse: MovieResponse): MovieResponseState()
    data class Error(val string: String): MovieResponseState()
    object Loading : MovieResponseState()
}