package com.example.cupcake

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cupcake.Data.DataSource
import com.example.cupcake.Adapter.SweetsListAdapter
import com.example.cupcake.databinding.FragmentSweetsBinding


class SweetsFragment : Fragment() {
    private var _binding: FragmentSweetsBinding? = null
    private val binding get() = _binding!!

    private val myDataset = DataSource.dataSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSweetsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = SweetsListAdapter(requireContext(), myDataset)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}