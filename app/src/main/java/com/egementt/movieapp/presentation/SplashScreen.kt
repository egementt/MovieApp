package com.egementt.movieapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.egementt.movieapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class SplashScreen : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launchWhenCreated {
            delay(2000)
            findNavController().navigate(R.id.action_splashScreen_to_homeFragment)
        }

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }


}