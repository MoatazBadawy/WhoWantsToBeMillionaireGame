package com.example.android.whowantstobemillionaire.ui.start

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentStartBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.utils.Audio

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {
    val audio = Audio()
    lateinit var mediaPlayer: MediaPlayer
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView() {
        mediaPlayer = MediaPlayer.create(this.context, R.raw.home_audio)
        audio.runAudio(mediaPlayer)

        binding.startBtn.setOnClickListener { v ->
            audio.pauseAudio(mediaPlayer)
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }

        binding.aboutBtn.setOnClickListener { v ->
            audio.pauseAudio(mediaPlayer)
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_aboutFragment)
        }
        binding.sound.setOnClickListener {
            sound()
        }
    }
        @RequiresApi(Build.VERSION_CODES.M)
        fun sound(){

            if(audio.muteState ==0)
            {
                audio.pauseAudio(mediaPlayer)
                audio.muteAudio(requireContext())
                audio.muteState = 100
                binding.sound.setImageResource(R.drawable.ic_audio_on)
            }
            else{
                audio.runAudio(mediaPlayer)
                audio.unmuteAudio(requireContext())
                audio.muteState = 0
                binding.sound.setImageResource(R.drawable.ic_audio_off)
            }

    }
}