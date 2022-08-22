package com.egementt.movieapp.presentation.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.egementt.movieapp.R
import com.egementt.movieapp.adapter.PopularMoviesRWAdapter
import com.egementt.movieapp.adapter.SearchMoviesRWAdapter
import com.egementt.movieapp.databinding.FragmentFavoritesBinding
import com.egementt.movieapp.presentation.SharedViewModel
import com.egementt.movieapp.presentation.state.BookmarkMovieState
import com.egementt.movieapp.presentation.state.MovieListState
import com.egementt.movieapp.util.MarginItemDecoration
import com.egementt.movieapp.util.ext.invisible
import com.egementt.movieapp.util.ext.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    val viewModel: FavoritesViewModel by viewModels()

    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)

        lifecycleScope.launchWhenStarted {
            viewModel.getFavoriteMovies()
            viewModel.favoriteState.collect{ state ->
                when(state){
                    is MovieListState.Loading -> {
                        binding.progressBar.visible()
                    }
                    is MovieListState.Success -> {
                        binding.progressBar.invisible()
                        binding.rwFavoriteMovies.apply {
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            adapter = SearchMoviesRWAdapter(state.movies, onClick = { movie ->
                                if (movie != null) {
                                    SharedViewModel.updateItem(movie)
                                }
                                findNavController().navigate(R.id.detailFragment)
                            })
                            addItemDecoration(MarginItemDecoration(12))
                        }
                    }
                    is MovieListState.Error -> {
                        binding.progressBar.invisible()
                        Log.d("FavoritesFragment", state.error)
                    }
                }
            }
        }

        return binding.root
    }

}