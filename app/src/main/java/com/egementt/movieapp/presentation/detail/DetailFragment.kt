package com.egementt.movieapp.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.egementt.movieapp.R
import com.egementt.movieapp.databinding.FragmentDetailBinding
import com.egementt.movieapp.presentation.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding : FragmentDetailBinding? = null
    val binding get() = _binding!!

    private val movieItem = SharedViewModel.selectedItem


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        movieItem.observe(viewLifecycleOwner, Observer {
           Glide.with(this).load(it.getFullImageURL(it.backdrop_path)).into(binding.iwPosterDetail)
        })

        return binding.root
    }

}