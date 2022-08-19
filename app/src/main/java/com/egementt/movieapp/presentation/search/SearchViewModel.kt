package com.egementt.movieapp.presentation.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {

    private val _searchState : MutableLiveData<SearchState> = MutableLiveData(SearchState.Loading)
    val searchState get() = _searchState




     fun searchByText(query: String){
        viewModelScope.launch {

                try {
                    val response = repository.searchByText(query).results.apply { map {
                        it.poster_path = it.getFullImageURL(resolution = Movie.Resolution.HIGH)
                    } }
                    _searchState.value = SearchState.Success(response)
                    Log.d("SearchViewModel", response.toString())
                }catch (e: Exception){
                    _searchState.value = SearchState.Error(e.message.toString())
                }
            }
        }
    }



sealed class SearchState {
    data class Success(val movies: List<Movie>) : SearchState()
    data class Error(val error: String) : SearchState()
    object Loading : SearchState()
}