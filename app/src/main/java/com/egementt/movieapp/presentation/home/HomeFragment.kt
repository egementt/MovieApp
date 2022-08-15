package com.egementt.movieapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenStarted
import com.egementt.movieapp.R
import com.egementt.movieapp.databinding.FragmentHomeBinding
import com.egementt.movieapp.presentation.HomeViewModel
import com.egementt.movieapp.presentation.MovieResponseState
import com.egementt.movieapp.util.ext.invisible
import com.egementt.movieapp.util.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding : FragmentHomeBinding? = null
    private val  binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenStarted {
            viewModel.movieListUiState.collect{ movieResponseState ->
                when(movieResponseState){
                    MovieResponseState.Loading -> {
                        binding.progressBar.visible()
                    }
                    is MovieResponseState.Success -> {
                        binding.progressBar.invisible()
                        binding.textView.apply {
                            text = movieResponseState.movieResponse.total_results.toString()
                            visible()
                        }
                    }
                        is MovieResponseState.Error -> {
                            binding.progressBar.invisible()
                            binding.textView.apply {
                                text = movieResponseState.string
                                visible()
                            }
                        }

                }
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}