package com.egementt.movieapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.egementt.movieapp.R
import com.egementt.movieapp.databinding.ActivityMainBinding
import com.egementt.movieapp.util.ext.gone
import com.egementt.movieapp.util.ext.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)


        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreen, R.id.detailFragment -> {
                    this.supportActionBar?.hide()
                    binding.bottomNavigation.gone()
                }
                else -> {
                    binding.bottomNavigation.visible()
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_homePage -> {
                    navHostFragment.navController.navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.item_searchPage ->{
                    navHostFragment.navController.navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true

                }
                R.id.item_favoritesPage -> {
                    navHostFragment.navController.navigate(R.id.favoritesFragment)
                    return@setOnItemSelectedListener true

                }
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}