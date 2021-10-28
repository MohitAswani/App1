package com.example.app1.ui.vaccines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.app1.R
import com.example.app1.databinding.FragmentVaccinesBinding

class VaccinesFragment : Fragment() {

    private lateinit var vaccinesViewModel: VaccinesViewModel
    private var _binding: FragmentVaccinesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vaccinesViewModel =
            ViewModelProvider(this).get(VaccinesViewModel::class.java)

        _binding = FragmentVaccinesBinding.inflate(inflater, container, false)

        binding.cardView1.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_navigation_home_to_covaxinData);
        }


        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}