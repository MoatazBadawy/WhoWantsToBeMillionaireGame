package com.example.android.whowantstobemillionaire.ui.view

import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentHomeBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel
import com.example.android.whowantstobemillionaire.util.helper.Constants

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val quizViewModel: QuizViewModel by viewModels()

    override fun setup() {
        binding.quizViewModel = quizViewModel
        getQuestion()
        setHelpMethodsButtons()
    }

    private fun setHelpMethodsButtons() {
        binding.removeButton.alpha = removebuttonAlpha
        binding.removeButton.isEnabled = isRemoveButtonEnabled
    }

    override fun callback() {
        binding.removeButton.setOnClickListener {
            if(countRemove < 1) {
                removeTwoAnswers()
                removebuttonAlpha = .5f
                isRemoveButtonEnabled = false
                it.alpha = removebuttonAlpha
                it.isEnabled = isRemoveButtonEnabled
            }
            countRemove++
        }

        binding.progressBar.setOnProgressChangeListener { progress ->
            if (progress == binding.progressBar.max) {
                navigateToResultsFragment()
                binding.progressBar.progressFromPrevious
            }
        }

        binding.replaceButton.setOnClickListener {
            if(countReplace < 1){
                count--
                navigateToHomeFragment()
            }
            countReplace++
        }

        binding.buttonSubmit.setOnClickListener{
            checkSelectedAnswer()
        }
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
        when(getQuizNum()){
            in 1..5 -> quizViewModel.getQuiz(Constants.EASY)
            in 6..10 -> quizViewModel.getQuiz(Constants.MEDIUM)
            in 11..15 -> quizViewModel.getQuiz(Constants.HARD)
            16 -> navigateToResultsFragment()
        }
    }

}