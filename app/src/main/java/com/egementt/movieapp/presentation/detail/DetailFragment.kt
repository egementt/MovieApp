package com.egementt.movieapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.egementt.movieapp.presentation.home.MovieResponseState
import com.egementt.movieapp.util.MarginItemDecoration
import com.egementt.movieapp.util.ext.invisible
import com.egementt.movieapp.util.ext.visible
import dagger.hilt.android.AndroidEntryPoint

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
            viewModel.getCast(it.id.toString())
            binding.apply {
                twMovieTitle.text = it.title
            }
           Glide.with(this).load(it.getFullImageURL(it.backdrop_path, Movie.Resolution.HIGH)).centerCrop().into(binding.iwPosterDetail)
        })

        lifecycleScope.launchWhenStarted {
            observePopularMovies()
        }

        return binding.root
    }

    private suspend fun observePopularMovies(){
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


}