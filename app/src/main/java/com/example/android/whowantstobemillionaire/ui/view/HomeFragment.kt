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

        binding.imageHelpDeleteTwo.setOnClickListener {
            if(countRemove < 1) {
                removeTwoAnswers()
//                it.alpha = .5f
            }
            countRemove++
        }

        binding.progressBar.setOnProgressChangeListener { progress ->
            if (progress == binding.progressBar.max) {
                navigateToResultsFragment()
                binding.progressBar.progressFromPrevious
            }
        }

        binding.imageHelpReplaceQuestion.setOnClickListener {
            if(countReplace < 1){
                count--
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
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

    fun checkSelectedAnswer() {
        for(i in 0..3){
            val radioButton = binding.radioGroupAnswers.getChildAt(i) as RadioButton
            when {
                radioButton.isChecked && !correctAnswer(i) -> {
                    navigateToResultsFragment()
                    break
                }
                radioButton.isChecked && correctAnswer(i) -> {
                    navigateToHomeFragment()
                    getQuestion()
                    break
                }
                else ->  continue
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