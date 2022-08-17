package com.egementt.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.egementt.movieapp.data.model.Movie

class SharedViewModel : ViewModel() {

    companion object{
         private val _selectedItem : MutableLiveData<Movie> = MutableLiveData<Movie>()
        val selectedItem get() = _selectedItem

        fun updateItem(movie: Movie){
            selectedItem.value = movie
        }

    }


}