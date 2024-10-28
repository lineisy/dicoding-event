package com.example.subfundamental.ui.upcoming

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subfundamental.databinding.FragmentUpcomingBinding
import com.example.subfundamental.ui.adapter.EventAdapter
import com.example.subfundamental.ui.main.DetailActivity
import com.example.subfundamental.ui.main.ProgressBarCallback
import com.example.subfundamental.ui.response.ListEventsItem

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingViewModel: UpcomingViewModel
    private lateinit var upcomingAdapter: EventAdapter
    private var progressBarCallback: ProgressBarCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProgressBarCallback) {
            progressBarCallback = context
        } else {
            throw RuntimeException("$context must implement ProgressBarCallback")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        upcomingViewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]
        upcomingAdapter = EventAdapter()
        observeViewModel()
        setupRecycler()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupRecycler() {
        binding.apply {

            recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewEvents.adapter = upcomingAdapter

        }
    }

    private fun navigateToDetail(idEvent: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EXTRA_ID_EVENT", idEvent)
        startActivity(intent)
    }

    private fun observeViewModel() {
        upcomingViewModel.apply {
            isloading.observe(viewLifecycleOwner) { isLoading ->
                showLoading(isLoading)
            }
            upcomingEvent.observe(viewLifecycleOwner) { events ->
                updateEventList(events)
            }
            errorMessage.observe(viewLifecycleOwner) {
                "Failed Fetching Data".showError()
            }
            findUpcomingEvent()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        progressBarCallback?.showProgressBar(isLoading)
    }

    private fun updateEventList(events: List<ListEventsItem>) {
        if (events.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.recyclerViewEvents.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.recyclerViewEvents.visibility = View.VISIBLE
            upcomingAdapter.submitList(events)
        }

    }

    private fun String.showError() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        progressBarCallback = null
    }
}