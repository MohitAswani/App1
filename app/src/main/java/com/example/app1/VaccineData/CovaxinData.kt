package com.example.app1.VaccineData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app1.R
import com.example.app1.databinding.FragmentCovaxinDataBinding

import com.google.android.material.bottomnavigation.BottomNavigationView




class CovaxinData : Fragment() {

    private lateinit var binding:FragmentCovaxinDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentCovaxinDataBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
}