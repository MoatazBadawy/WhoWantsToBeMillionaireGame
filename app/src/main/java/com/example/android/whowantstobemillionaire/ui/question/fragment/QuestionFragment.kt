package com.example.android.whowantstobemillionaire.ui.question.fragment

import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentQustionBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.question.viewmodel.QuestionViewModel
import com.example.android.whowantstobemillionaire.utils.Audio

class QuestionFragment : BaseFragment<FragmentQustionBinding>(R.layout.fragment_qustion) {
    private val quizViewModel: QuestionViewModel by viewModels()
    private val audio = Audio()
    private lateinit var mediaPlayer: MediaPlayer

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView() {
        binding.questionViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.question_audio)
        audio.runAudio(mediaPlayer)

        quizViewModel.losingNavigate.observe(
            viewLifecycleOwner
        ) {

            if (it) {
                errorSound()
                navigateToLosingFragment()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun errorSound() {
        audio.pauseAudio(mediaPlayer)
        audio.runAudio(MediaPlayer.create(requireContext(), R.raw.error_audio))
    }

    private fun onClick() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.home_audio)
        binding.answer1.setOnClickListener {
            audio.runAudio(mediaPlayer)
        }
        binding.answer2.setOnClickListener {
            audio.runAudio(mediaPlayer)
        }
        binding.answer3.setOnClickListener {
            audio.runAudio(mediaPlayer)
        }
        binding.answer4.setOnClickListener {
            audio.runAudio(mediaPlayer)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        audio.pauseAudio(mediaPlayer)
    }

    private fun navigateToLosingFragment() {
        requireView().findNavController().navigate(R.id.action_homeFragment_to_losingFragment)
    }
}