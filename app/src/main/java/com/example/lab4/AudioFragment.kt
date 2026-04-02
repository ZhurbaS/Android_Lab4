package com.example.lab4

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class AudioFragment : Fragment(R.layout.fragment_audio) {

    private var mediaPlayer: MediaPlayer? = null
    private var currentUri: Uri? = null
    private lateinit var tvStatus: TextView

    @SuppressLint("SetTextI18n")
    private val pickAudioLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            currentUri = uri
            tvStatus.text = "Файл обрано! Натисніть Play"
            prepareMediaPlayer(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvStatus = view.findViewById(R.id.tvAudioStatus)

        view.findViewById<Button>(R.id.btnPickAudio).setOnClickListener {
            pickAudioLauncher.launch("audio/*")
        }

        view.findViewById<Button>(R.id.btnPlay).setOnClickListener {
            mediaPlayer?.start()
            tvStatus.text = "Відтворення..."
        }

        view.findViewById<Button>(R.id.btnPause).setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                tvStatus.text = "Пауза"
            }
        }

        view.findViewById<Button>(R.id.btnStop).setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.prepare()
            mediaPlayer?.seekTo(0)
            tvStatus.text = "Зупинено"
        }

        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun prepareMediaPlayer(uri: Uri) {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(requireContext(), uri)
                prepare()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Помилка файлу: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}