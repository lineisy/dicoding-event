package com.example.subfundamental.ui.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subfundamental.R
import com.example.subfundamental.data.repository.Factory
import com.example.subfundamental.databinding.FragmentFavoriteBinding
import com.example.subfundamental.ui.adapter.EventAdapter
import com.example.subfundamental.ui.adapter.FavoriteAdapter
import com.example.subfundamental.ui.main.DetailViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FavoriteViewModel
//    private val viewModel by viewModels<FavoriteViewModel>{
//        Factory.getInstance(requireActivity())
//    }

    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val factory: Factory = Factory.getInstance(requireContext())
        viewModel = ViewModelProvider(requireActivity(),factory)[FavoriteViewModel::class.java]

        adapter = FavoriteAdapter()
        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.adapter = adapter

        viewModel.getFavEvent().observe(viewLifecycleOwner){ result ->
            adapter.submitList(result)
        }

        return root
    }

}