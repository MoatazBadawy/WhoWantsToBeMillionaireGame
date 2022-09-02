package com.example.android.whowantstobemillionaire.ui.view

import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentHomeBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel
import com.example.android.whowantstobemillionaire.util.*
import com.example.android.whowantstobemillionaire.util.helper.Constants
import com.example.android.whowantstobemillionaire.util.helper.disable

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val quizViewModel: QuizViewModel by viewModels()

    override fun setup() {
        binding.quizViewModel = quizViewModel
        setHelpMethodsButtons()
        getQuestion()
        (requireActivity()as AppCompatActivity).supportActionBar?.hide()

    }

    private fun setHelpMethodsButtons() {
        if (isRemoved) {
            binding.removeButton.disable()
        }

        if (isReplaced){
            binding.replaceButton.disable()
        }
    }

    override fun callback() {
        binding.removeButton.setOnClickListener {
            onRemove()
        }

        binding.progressBar.setOnProgressChangeListener { progress ->
            onProgress(progress)
        }

        binding.replaceButton.setOnClickListener {
            onReplace()
        }

        binding.buttonSubmit.setOnClickListener {
            checkSelectedAnswer()
        }

        binding.imageQuit.setOnClickListener{
            navigateToStartFragment()
        }

        binding.imageLevels.setOnClickListener{
            navigateToResultsFragment()
        }

    }

    private fun navigateToStartFragment() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_startFragment)
    }

    private fun onReplace() {
        if (countReplace < 1) {
            count--
            navigateToHomeFragment()
            binding.replaceButton.disable()
            isReplaced = true
        }
        countReplace++
    }

    private fun onProgress(progress: Float) {
        if (progress == binding.progressBar.max) {
            navigateToResultsFragment()
            binding.progressBar.progressFromPrevious
        }
    }

    private fun onRemove() {
        if (countRemove < 1) {
            removeTwoAnswers()
            binding.removeButton.disable()
            isRemoved = true
        }
        countRemove++
    }

    private fun navigateToResultsFragment() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_resultFragment)
    }

    private fun navigateToHomeFragment() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_self)
    }

    private fun removeTwoAnswers() {
        var count = 0
        for (i in 0..3) {
            val radioButton = binding.radioGroupAnswers.getChildAt(i) as RadioButton
            if (!correctAnswer(i)) {
                radioButton.visibility = View.INVISIBLE
                count++
                if (count == 2) break
            }
        }
    }

    private fun checkSelectedAnswer() {
        val selectedAnswer = binding.radioGroupAnswers.checkedRadioButtonId
        if (selectedAnswer != -1) {
            val radioButton = binding.radioGroupAnswers.findViewById<RadioButton>(selectedAnswer)
            val index = binding.radioGroupAnswers.indexOfChild(radioButton)
            if (correctAnswer(index)) {
                navigateToHomeFragment()
                getQuestion()
            } else {
                navigateToResultsFragment()
            }
        }
    }

    private fun getQuestion() {
        when (getQuizNum()) {
            in 1..5 -> quizViewModel.getQuiz(Constants.EASY)
            in 6..10 -> quizViewModel.getQuiz(Constants.MEDIUM)
            in 11..15 -> quizViewModel.getQuiz(Constants.HARD)
            16 -> navigateToResultsFragment()
        }
    }

}