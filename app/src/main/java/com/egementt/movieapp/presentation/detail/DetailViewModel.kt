package com.egementt.movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.model.Cast
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.data.model.MovieResponse
import com.egementt.movieapp.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _castState: MutableStateFlow<CastResponseState> = MutableStateFlow(
        CastResponseState.Loading
    )
    val castState get() = _castState

    private val _recommendedMoviesState: MutableStateFlow<RecommendedMovieResponseState> = MutableStateFlow(
        RecommendedMovieResponseState.Loading
    )
    val recommendedMovieResponseState get() = _recommendedMoviesState


    fun getCast(movieId: String) {
        viewModelScope.launch {
            _castState.collect {
                try {
                    val res = repository.getMovieCredits(movieId).cast.apply {
                        map {
                            it.profile_path = it.createFullImageUrl()
                        }
                    }
                    _castState.value = CastResponseState.Success(res)
                } catch (e: Exception) {
                    _castState.value = CastResponseState.Error(e.message ?: "An error occurred")
                }
            }
        }

    }

    fun getRecommendedMovies(movieId: String){
        viewModelScope.launch {
            _recommendedMoviesState.collect{
                try {
                    val res = repository.getRecommendedMovies(movieId).results.apply {
                        map {
                            it.poster_path = it.getFullImageURL(it.poster_path)
                        }
                    }
                    _recommendedMoviesState.value = RecommendedMovieResponseState.Success(res)
                }catch (e: Exception){
                    _recommendedMoviesState.value = RecommendedMovieResponseState.Error(e.message ?: "An error occurred")
                }
            }
        }
    }

    sealed class RecommendedMovieResponseState {
        data class Success(val movies: List<Movie>) : RecommendedMovieResponseState()
        data class Error(val error: String) : RecommendedMovieResponseState()
        object Loading : RecommendedMovieResponseState()
    }

    sealed class CastResponseState {
        data class Success(val casts: List<Cast>) : CastResponseState()
        data class Error(val string: String) : CastResponseState()
        object Loading : CastResponseState()
    }
}