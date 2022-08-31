package com.example.android.whowantstobemillionaire.ui.view

import android.view.View
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentHomeBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val quizViewModel: QuizViewModel by viewModels()
    override fun setup() {
        binding.quizViewModel = quizViewModel
        quizViewModel.getEasyQuiz()
        binding.imageHelpDeleteTwo.setOnClickListener { removeTwoAnswers() }
        binding.buttonSubmit.setOnClickListener { navigateToResultsFragment() }
        binding.progressBar.setOnProgressChangeListener {
            if (it >= 30f) {
                navigateToResultsFragment()
            }
        }

    }

    private fun navigateToResultsFragment() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_resultFragment)
    }

    private fun removeTwoAnswers() {
        var count = 0
        for (i in 0..3) {
            val radioButton = binding.radioGroupAnswers.getChildAt(i) as RadioButton
            if (!trueAnswer(i)) {
                radioButton.visibility = View.INVISIBLE
                count++
                if (count == 2) break
            }
        }
    }


}