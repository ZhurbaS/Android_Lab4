package com.example.lab4

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class MenuFragment : Fragment(R.layout.fragment_menu) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnOpenAudio).setOnClickListener {
            (activity as MainActivity).loadFragment(AudioFragment())
        }

        view.findViewById<Button>(R.id.btnOpenVideo).setOnClickListener {
            (activity as MainActivity).loadFragment(VideoFragment())
        }
    }
}