package com.example.subfundamental.ui.finished

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
import com.example.subfundamental.R
import com.example.subfundamental.databinding.FragmentFinishedBinding
import com.example.subfundamental.ui.adapter.EventAdapter
import com.example.subfundamental.ui.main.DetailActivity
import com.example.subfundamental.ui.main.ProgressBarCallback
import com.example.subfundamental.ui.response.ListEventsItem

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var finishedViewModel: FinishedViewModel
    private lateinit var finishedAdapter: EventAdapter
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
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        finishedViewModel = ViewModelProvider(this)[FinishedViewModel::class.java]
        finishedAdapter = EventAdapter()
        setupRecycler()
        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupRecycler() {
        binding.apply {

            recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewEvents.adapter = finishedAdapter


        }
    }

    private fun navigateToDetail(idEvent: Int) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("EXTRA_ID_EVENT", idEvent)
        startActivity(intent)
    }

    private fun observeViewModel() {
        finishedViewModel.apply {
            isloading.observe(viewLifecycleOwner) { isLoading ->
                showLoading(isLoading)
            }
            finishedEvent.observe(viewLifecycleOwner) { events ->
                updateEventList(events)
            }
            errorMessage.observe(viewLifecycleOwner) {
                getString(R.string.error_message).showError()
            }
            findFinishedEvent()
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
        binding.apply {
            if (events.isEmpty()) {
                tvNoData.visibility = View.VISIBLE
                recyclerViewEvents.visibility = View.GONE
            } else {
                tvNoData.visibility = View.GONE
                recyclerViewEvents.visibility = View.VISIBLE
                finishedAdapter.submitList(events)
            }
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