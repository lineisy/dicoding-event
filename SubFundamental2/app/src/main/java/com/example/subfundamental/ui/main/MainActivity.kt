package com.example.subfundamental.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.subfundamental.R
import com.example.subfundamental.data.repository.Factory
import com.example.subfundamental.databinding.ActivityMainBinding
import com.example.subfundamental.ui.settings.SettingsViewModel

interface ProgressBarCallback {
    fun showProgressBar(show: Boolean)
}

class MainActivity : AppCompatActivity(), ProgressBarCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: Factory = Factory.getInstance(this)
        viewModel = ViewModelProvider(this,factory)[SettingsViewModel::class.java]
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        progressBar = binding.progressBar
        showProgressBar(false)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_upcoming,
                R.id.navigation_finished,
                R.id.navigation_favorite,
                R.id.navigation_settings
            )
        )

        viewModel.getThemeSetting().observe(this@MainActivity){
            darkMode(it)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun darkMode(isDarkMode : Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE

    }
}
