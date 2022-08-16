package com.egementt.movieapp.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.egementt.movieapp.R
import com.egementt.movieapp.adapter.PopularMoviesRWAdapter
import com.egementt.movieapp.databinding.FragmentHomeBinding
import com.egementt.movieapp.presentation.HomeViewModel
import com.egementt.movieapp.presentation.MovieResponseState
import com.egementt.movieapp.util.MarginItemDecoration
import com.egementt.movieapp.util.ext.invisible
import com.egementt.movieapp.util.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenStarted {
            observePopularMovies()
        }
        lifecycleScope.launchWhenStarted {
            observeUpcomingMovies()
        }




        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun observePopularMovies(){
        viewModel.popularMovieListUiState.collect { movieResponseState ->
            when (movieResponseState) {
                MovieResponseState.Loading -> {
                    binding.pbPopularMovies.visible()
                }
                is MovieResponseState.Success -> {
                    binding.pbPopularMovies.invisible()

                    binding.rwPopularMovies.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                        adapter = PopularMoviesRWAdapter(movieResponseState.movieResponse.results, onClick = { movie ->
                            findNavController().navigate(R.id.detailFragment)
                        })
                        addItemDecoration(MarginItemDecoration(12))
                    }
                }
                is MovieResponseState.Error -> {
                    binding.pbPopularMovies.invisible()

                }

            }
        }
    }

    private suspend fun observeUpcomingMovies(){
        viewModel.upcomingMovieListUiState.collect { movieResponseState ->
            when (movieResponseState) {
                MovieResponseState.Loading -> {
                    binding.pbUpcomingMovies.visible()
                }
                is MovieResponseState.Success -> {
                    binding.pbUpcomingMovies.invisible()

                    binding.rwUpcomingMovies.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        setOnClickListener {
                            findNavController().navigate(R.id.detailFragment)
                        }
                        adapter = PopularMoviesRWAdapter(movieResponseState.movieResponse.results, onClick = { movie ->
                            findNavController().navigate(R.id.detailFragment)
                        })
                        addItemDecoration(MarginItemDecoration(12))
                    }
                }
                is MovieResponseState.Error -> {
                    binding.pbPopularMovies.invisible()

                }

            }
        }
    }
}