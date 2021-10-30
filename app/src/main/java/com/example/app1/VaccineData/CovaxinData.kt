package com.example.app1.VaccineData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.app1.databinding.FragmentCovaxinDataBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import androidx.annotation.NonNull

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

import android.R

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView



class CovaxinData : Fragment() {


    private lateinit var binding: FragmentCovaxinDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCovaxinDataBinding.inflate(inflater, container, false)
        val youTubePlayerView: YouTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "FtX0mLCqKXU"
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

        val root: View = binding.root
        return root


    }

}