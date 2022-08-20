package com.egementt.movieapp.presentation.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.egementt.movieapp.R
import com.egementt.movieapp.adapter.CastRWAdapater
import com.egementt.movieapp.adapter.PopularMoviesRWAdapter
import com.egementt.movieapp.data.model.Movie
import com.egementt.movieapp.databinding.FragmentDetailBinding
import com.egementt.movieapp.presentation.SharedViewModel
import com.egementt.movieapp.util.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : DetailViewModel by viewModels()
    private val movieItem = SharedViewModel.selectedItem


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        movieItem.observe(viewLifecycleOwner, Observer {
            viewModel.apply {
                getCast(it.id.toString())
                getRecommendedMovies(it.id.toString())
            }
            binding.apply {
                twMovieTitle.text = it.title
            }
           Glide.with(this).load(it.getFullImageURL(it.backdrop_path, Movie.Resolution.HIGH)).centerCrop().into(binding.iwPosterDetail)
        })

        binding.btnWatch.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_videoPlayerFragment)
        }

        lifecycleScope.launchWhenStarted {
            observeCasts()
        }
        lifecycleScope.launchWhenStarted {
            observeRecommendedMovies()
        }


        binding.btnBookmark.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                observeBookmarks(movieItem.value!!)
            }
        }

        return binding.root
    }

    private suspend fun observeCasts(){
        viewModel.castState.collect { castState ->
            when (castState) {
                DetailViewModel.CastResponseState.Loading -> {
                }
                is DetailViewModel.CastResponseState.Success -> {

                    binding.rwCast.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                        adapter = CastRWAdapater(castState.casts)
                        addItemDecoration(MarginItemDecoration(12))
                    }
                }
                is DetailViewModel.CastResponseState.Error -> {


                }

            }
        }
    }

    private suspend fun observeRecommendedMovies(){
        viewModel.recommendedMovieResponseState.collect{ responseState ->
            when(responseState){
                DetailViewModel.RecommendedMovieResponseState.Loading -> {

                }
                is DetailViewModel.RecommendedMovieResponseState.Success -> {
                    binding.rwRecommendedMovies.apply {
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = PopularMoviesRWAdapter(responseState.movies, onClick = { movie ->
                            if (movie != null) {
                                SharedViewModel.updateItem(movie)
                            }
                            findNavController().navigate(R.id.detailFragment)
                        })
                        addItemDecoration(MarginItemDecoration(12))
                    }
                }
                is DetailViewModel.RecommendedMovieResponseState.Error -> {
                    Toast.makeText(requireContext(), responseState.error, Toast.LENGTH_LONG).show()
                    Log.e("DetailFragment", responseState.error)
                }
            }

        }
    }

    private suspend fun observeBookmarks(movie: Movie){
        viewModel.bookmarkMovie(movieItem.value!!)
        viewModel.bookmarkMovieState.collectLatest {  state->
            when(state){
                is DetailViewModel.BookmarkMovieState.Unmarked -> {

                }
                is DetailViewModel.BookmarkMovieState.Success -> {
                    binding.btnBookmark.imageTintList = ColorStateList.valueOf(Color.RED)
                }
                is DetailViewModel.BookmarkMovieState.Error -> {
                Log.d("DetailFragment", state.error.toString())
                }
            }
        }
    }


}