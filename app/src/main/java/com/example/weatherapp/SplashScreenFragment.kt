package com.example.weatherapp

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.navigation.fragment.findNavController

class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val videoView = view.findViewById<VideoView>(R.id.videoSplash)

        val videoUri = Uri.parse("android.resource://com.example.weatherapp/${R.raw.movingsun}")
        videoView.setVideoURI(videoUri)
        videoView.start()
        videoView.setOnCompletionListener {
            findNavController().navigate(R.id.action_splashScreenFragment_to_weatherFragment)
        }
    Handler(Looper.getMainLooper()).postDelayed(
    {
        findNavController().navigate(R.id.action_splashScreenFragment_to_weatherFragment)
    },8000)

}
}
