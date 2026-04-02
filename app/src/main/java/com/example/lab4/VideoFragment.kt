package com.example.lab4

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.VideoView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class VideoFragment : Fragment(R.layout.fragment_video) {

    private lateinit var videoView: VideoView
    private lateinit var etUrl: EditText

    private val pickVideoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            playVideo(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = view.findViewById(R.id.videoView)
        etUrl = view.findViewById(R.id.etUrl)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        view.findViewById<Button>(R.id.btnPickVideo).setOnClickListener {
            pickVideoLauncher.launch("video/*")
        }

        view.findViewById<Button>(R.id.btnPlayUrl).setOnClickListener {
            val url = etUrl.text.toString()
            if (url.isNotEmpty()) {
                playVideo(Uri.parse(url))
            } else {
                Toast.makeText(context, "Введіть посилання!", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun playVideo(uri: Uri) {
        try {
            videoView.setVideoURI(uri)
            videoView.start()
            Toast.makeText(context, "Завантаження...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Помилка відтворення", Toast.LENGTH_SHORT).show()
        }
    }
}