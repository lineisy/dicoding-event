package com.example.subfundamental.ui.settings

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.subfundamental.R
import com.example.subfundamental.data.repository.Factory
import com.example.subfundamental.databinding.FragmentSettingsBinding
import com.example.subfundamental.ui.favorite.FavoriteViewModel
import java.util.concurrent.TimeUnit

class SettingsFragment : PreferenceFragmentCompat() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var viewModel : SettingsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory : Factory = Factory.getInstance(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)[SettingsViewModel::class.java]
        val themeButton = findPreference<SwitchPreference>("keyTheme")

        viewModel.getThemeSetting().observe(viewLifecycleOwner){ isDark->
            themeButton?.isChecked = isDark
        }

        themeButton?.setOnPreferenceChangeListener{ _, newValue ->
            val isDark = newValue as Boolean
            viewModel.setThemeSetting(isDark)
            setTheme(isDark)
            true
        }
    }
//    private fun startPriodic() {
//        val workRequest: WorkRequest = PeriodicWorkRequestBuilder<Worker>(1, TimeUnit.DAYS)
//            .build()
//        WorkManager.getInstance(requireContext()).enqueue(workRequest)
//    }

    private fun setTheme(isDark: Boolean) {
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)
    }
}