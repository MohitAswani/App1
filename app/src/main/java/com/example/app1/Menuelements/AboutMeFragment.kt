package com.example.app1

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app1.R
import com.example.app1.databinding.FragmentAboutmeBinding
import android.content.Intent
import android.net.Uri


class FragmentAboutme : Fragment() {

    private lateinit var binding:FragmentAboutmeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAboutmeBinding.inflate(inflater, container, false)
        val root:View=binding.root

        binding.textViewLinkDin1.setOnClickListener{GetUrlFromIntent1(it)}

        binding.textViewLinkDin2.setOnClickListener{GetUrlFromIntent2(it)}
        return root
    }

    fun GetUrlFromIntent1(view: View?) {
        val url = "https://www.linkedin.com/in/mohit-aswani-345ab31bb/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun GetUrlFromIntent2(view: View?) {
        val url = "https://www.linkedin.com/in/akshat-saxena-788a18202/"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}