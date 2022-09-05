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
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView() {
        audio.runAudio(MediaPlayer.create(context, R.raw.home_audio))
        binding.startBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }

        binding.aboutBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_aboutFragment)
        }
        binding.sound.setOnClickListener {
            if(audio.muteState ==100)
            {
                audio.muteAudio(requireContext())
                audio.muteState =0
                binding.sound.setImageResource(R.drawable.ic_audio_on)
            }
            else{
                audio.runAudio(MediaPlayer.create(context, R.raw.home_audio))
                audio.unmuteAudio(requireContext())
                audio.muteState =100
                binding.sound.setImageResource(R.drawable.ic_audio_off)
            }




        }
    }
}