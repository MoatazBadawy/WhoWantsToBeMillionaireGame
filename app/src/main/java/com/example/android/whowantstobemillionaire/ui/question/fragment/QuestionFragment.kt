package com.example.android.whowantstobemillionaire.ui.question.fragment

import android.app.AlertDialog
import android.media.MediaPlayer
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentQustionBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.question.viewmodel.QuestionViewModel
import com.example.android.whowantstobemillionaire.utils.Audio
import com.example.android.whowantstobemillionaire.utils.helper.PrefForLastCoinsYouWin
import com.example.android.whowantstobemillionaire.utils.helper.disable

class QuestionFragment :
    BaseFragment<FragmentQustionBinding>
        (R.layout.fragment_qustion) {

    private val quizViewModel: QuestionViewModel by viewModels()

    private val audio = Audio()
    private lateinit var mediaPlayer: MediaPlayer

    var shared = PrefForLastCoinsYouWin()

    override fun onCreateView() {
        binding.questionViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        saveCoins()
        initAudio()
        leaveQuestion()
        losingNavigate()
        resultNavigate()
        onClickOnce()
    }

    private fun initAudio() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.question_audio)
        mediaPlayer.isLooping = true
        audio.runAudio(mediaPlayer)
    }

    private fun errorSound() {
        audio.pauseAudio(mediaPlayer)
        audio.runAudio(MediaPlayer.create(requireContext(), R.raw.error_audio))
    }

    private fun saveCoins() {
        quizViewModel.coins.observe(viewLifecycleOwner) { coins ->
            shared.saveLastResult(
                requireActivity(),
                coins
            )
        }
    }

    private fun leaveQuestion() {
        quizViewModel.leaveQuestion.observe(
            viewLifecycleOwner
        ) {
            if (it) backQuizAlertDialog()
        }
    }

    private fun losingNavigate() {
        quizViewModel.losingNavigate.observe(
            viewLifecycleOwner
        ) {
            if (it) {
                errorSound()
                navigateToLosingFragment()
            }
        }
    }

    private fun resultNavigate() {
        quizViewModel.resultNavigate.observe(
            viewLifecycleOwner
        ) {
            if (it) navigateToResultFragment()
        }
    }

    private fun onClickOnce() {
        quizViewModel.changeQuestionClickOnce.observe(
            viewLifecycleOwner
        ) {
            if (it) changeQuestionHelperOnClick()
        }

        quizViewModel.removeQuestionClickOnce.observe(
            viewLifecycleOwner
        ) {
            if (it) remove2AnswersOnClick()
        }
    }

    private fun navigateToLosingFragment() {
        requireView().findNavController().navigate(R.id.action_questionFragment_to_losingFragment)
    }

    private fun navigateToResultFragment() {
        requireView().findNavController().navigate(R.id.action_questionFragment_to_resultFragment)
    }

    private fun changeQuestionHelperOnClick() {
        binding.changeQuestion.disable()
    }

    private fun remove2AnswersOnClick() {
        binding.removeTwoAnswers.disable()
    }


    private fun backQuizAlertDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Exit")
            setMessage("Do you really want to withdraw from the competition?")
            setPositiveButton("yes") { _, _ ->
                requireView().findNavController().popBackStack()
            }
            setNegativeButton("No") { it, _ ->
                it.cancel()
            }
        }
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        audio.pauseAudio(mediaPlayer)
    }
    override fun onStop() {
        super.onStop()
        audio.pauseAudio(mediaPlayer)
    }
}