package com.egementt.movieapp.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NavUtils
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.egementt.movieapp.R
import com.egementt.movieapp.adapter.PopularMoviesRWAdapter
import com.egementt.movieapp.adapter.SearchMoviesRWAdapter
import com.egementt.movieapp.databinding.FragmentSearchBinding
import com.egementt.movieapp.presentation.SharedViewModel
import com.egementt.movieapp.util.MarginItemDecoration
import com.egementt.movieapp.util.ext.invisible
import com.egementt.movieapp.util.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        binding.btnSearch.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                searchViewModel.searchByText(binding.etSearch.text.toString().trim())
                binding.rwSearch.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                searchViewModel.searchState.observe(viewLifecycleOwner, Observer { state ->
                    when (state) {
                        SearchState.Loading -> {
                            binding.progressBar.visible()
                        }
                        is SearchState.Success -> {
                            binding.progressBar.invisible()
                            binding.rwSearch.apply {

                                adapter = SearchMoviesRWAdapter(state.movies, onClick = { movie ->
                                    if (movie != null) {
                                        SharedViewModel.updateItem(movie)
                                    }
                                    findNavController().navigate(R.id.detailFragment)
                                })
                            }
                        }
                        is SearchState.Error -> {
                            binding.progressBar.invisible()

                        }
                    }

                })

            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}