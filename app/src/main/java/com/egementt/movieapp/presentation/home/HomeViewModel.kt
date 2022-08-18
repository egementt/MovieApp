package com.egementt.movieapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.model.MovieResponse
import com.egementt.movieapp.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MovieRepository ) : ViewModel() {

    private val _popularMovieListState : MutableStateFlow<MovieResponseState> = MutableStateFlow(
        MovieResponseState.Loading
    )
    val popularMovieListUiState get() = _popularMovieListState

    private val _upcomingMovieListState : MutableStateFlow<MovieResponseState> = MutableStateFlow(
        MovieResponseState.Loading
    )
    val upcomingMovieListUiState get() = _upcomingMovieListState


    init {
        getPopularMovies()
        getUpcomingMovies()
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            _popularMovieListState.collect{
                try {
                     val res = repository.getPopularMovies().apply {
                        results.subList(0,10).map { movie->
                            movie.poster_path = movie.getFullImageURL()
                        } }
                    _popularMovieListState.value = MovieResponseState.Success(res)
                }catch (e: Exception){
                    _popularMovieListState.value =
                        MovieResponseState.Error(e.message ?: "An error occurred")
                    Log.e("HomeViewModel", e.message ?: "Error")

                }
                Log.d("HomeViewModel", it.toString())

            }
        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch {
            _upcomingMovieListState.collect {
                try {
                    val res = repository.getUpcomingMovies().apply {
                        results.subList(0,10).map { movie->
                            movie.poster_path = movie.getFullImageURL()
                        } }
                    _upcomingMovieListState.value = MovieResponseState.Success(res)
                    Log.d("HomeViewModel_Upcoming", it.toString())
                }catch (e: Exception){
                    _upcomingMovieListState.value =
                        MovieResponseState.Error(e.message ?: "An error occured")
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