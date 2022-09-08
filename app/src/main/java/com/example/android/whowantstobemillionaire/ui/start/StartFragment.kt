package com.example.android.whowantstobemillionaire.ui.start

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentStartBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.utils.Audio
import com.example.android.whowantstobemillionaire.utils.helper.PrefForLastCoinsYouWin

class StartFragment : BaseFragment<FragmentStartBinding>
    (R.layout.fragment_start) {

    private val audio = Audio()
    private lateinit var mediaPlayer: MediaPlayer

    var shared = PrefForLastCoinsYouWin()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView() {
        binding.lifecycleOwner = viewLifecycleOwner

        mediaPlayer = MediaPlayer.create(this.context, R.raw.home_audio)
        firstRunAudio(mediaPlayer)

        binding.startBtn.setOnClickListener { v ->
            audio.pauseAudio(mediaPlayer)
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }

        binding.aboutBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_aboutFragment)
        }

        binding.resultBtn.setOnClickListener {
            loadLastResult()
        }

        binding.sound.setOnClickListener {
            sound()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun firstRunAudio(mediaPlayer: MediaPlayer) {
        audio.runAudio(mediaPlayer)
        binding.sound.setImageResource(R.drawable.ic_audio_on)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun sound() {
        if (audio.muteState == 100) {
            audio.muteAudio(requireContext())
            binding.sound.setImageResource(R.drawable.ic_audio_off)
        } else {
            audio.unmuteAudio(requireContext())
            binding.sound.setImageResource(R.drawable.ic_audio_on)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadLastResult() {
        Navigation.findNavController(recy).navigate(R.id.action_startFragment_to_lastResultFragment)
//        val result = shared.loadLastResult(requireActivity())
//        binding.resultBtn.text = "Last Coins You Won is ${result} $"
    }

    override fun onDestroy() {
        super.onDestroy()
        audio.pauseAudio(mediaPlayer)
    }
}