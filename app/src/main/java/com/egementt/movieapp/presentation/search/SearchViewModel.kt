package com.egementt.movieapp.presentation.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.data.remote.MovieRepository
import com.egementt.movieapp.presentation.state.MovieListState
import com.egementt.movieapp.presentation.state.MovieResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _searchState : MutableLiveData<MovieResponseState> = MutableLiveData(MovieResponseState.Loading)
    val searchState get() = _searchState




     fun searchByText(query: String){
        viewModelScope.launch {

                try {
                    val response = repository.searchByText(query).apply { results.map {
                        it.poster_path = it.getFullImageURL(resolution = Movie.Resolution.HIGH)
                    } }
                    _searchState.value = MovieResponseState.Success(response)
                    Log.d("SearchViewModel", response.toString())
                }catch (e: Exception){
                    _searchState.value = MovieResponseState.Error(e.message.toString())
                }
            }
        }
    }



