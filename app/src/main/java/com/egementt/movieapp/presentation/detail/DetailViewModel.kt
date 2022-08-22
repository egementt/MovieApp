package com.egementt.movieapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.local.LocalRepository
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.data.remote.MovieRepository
import com.egementt.movieapp.presentation.state.BookmarkMovieState
import com.egementt.movieapp.presentation.state.CastState
import com.egementt.movieapp.presentation.state.MovieResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _castState: MutableStateFlow<CastState> = MutableStateFlow(
        CastState.Loading
    )
    val castState get() = _castState

    private val _recommendedMoviesState: MutableStateFlow<MovieResponseState> =
        MutableStateFlow(
            MovieResponseState.Loading
        )
    val recommendedMovieResponseState get() = _recommendedMoviesState

    private val _bookmarkMovieState: MutableStateFlow<BookmarkMovieState> = MutableStateFlow(
        BookmarkMovieState.Unmarked
    )
    val bookmarkMovieState get() = _bookmarkMovieState


    fun getCast(movieId: String) {
        viewModelScope.launch {
            _castState.collect {
                try {
                    val res = repository.getMovieCredits(movieId).cast.apply {
                        map {
                            it.profile_path = it.createFullImageUrl()
                        }
                    }
                    _castState.value = CastState.Success(res)
                } catch (e: Exception) {
                    _castState.value = CastState.Error(e.message ?: "An error occurred")
                }
            }
        }

    }

    fun getRecommendedMovies(movieId: String) {
        viewModelScope.launch {
            _recommendedMoviesState.collect {
                try {
                    val res = repository.getRecommendedMovies(movieId).apply {
                        results.map {
                            it.poster_path = it.getFullImageURL(it.poster_path)
                        }
                    }
                    _recommendedMoviesState.value = MovieResponseState.Success(res)
                } catch (e: Exception) {
                    _recommendedMoviesState.value =
                        MovieResponseState.Error(e.message ?: "An error occurred")
                }
            }
        }
    }


    fun bookmarkMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            _bookmarkMovieState.collect {
                try {
                    localRepository.insertMovie(movie)
                    _bookmarkMovieState.value = BookmarkMovieState.Success
                } catch (
                    e: Exception
                ){
                    _bookmarkMovieState.value = BookmarkMovieState.Error(e.message.toString())
                }
            }
        }
    }






}