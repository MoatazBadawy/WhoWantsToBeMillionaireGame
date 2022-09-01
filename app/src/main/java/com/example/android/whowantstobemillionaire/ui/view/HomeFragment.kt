package com.example.android.whowantstobemillionaire.ui.view

import android.util.Log
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


        binding.imageHelpDeleteTwo.setOnClickListener {
            if(countRemove < 1)  removeTwoAnswers()
            countRemove++
        }

        binding.progressBar.setOnProgressChangeListener {
            if (it >= 30f) {
                navigateToResultsFragment()
                binding.progressBar.progressFromPrevious
            }
        }


        binding.imageHelpReplaceQuestion.setOnClickListener {
            if(countReplace < 1){
                Navigation.findNavController(it).navigate(R.id.action_homeFragment_self)
                //binding.progressBar.progressFromPrevious
            }
            countReplace++
        }

        binding.buttonSubmit.setOnClickListener{v ->

            checkSelectedAnswer(v)}
    }

    fun navigateToResultsFragment() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_resultFragment)
    }

    fun removeTwoAnswers() {
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

    fun checkSelectedAnswer(view: View){
        for(i in 0..3){
            val radioButton = binding.radioGroupAnswers.getChildAt(i) as RadioButton
            if(radioButton.isChecked && !trueAnswer(i)) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_resultFragment)
                break
            }else if(radioButton.isChecked && trueAnswer(i)){
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_self)
                //var num = getQuizNum()
                //binding.quizNum.text = num.toString()
                //binding.textScore.text = getQuizCoins().toString()

                when(getQuizNum()){
                    in 1..5 -> quizViewModel.getEasyQuiz()
                    in 6..10 -> quizViewModel.getMediumQuiz()
                    in 11..14 -> quizViewModel.getHardQuiz()
                    15 -> Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_resultFragment)
                }
                break
            }else{
                continue
            }
        }
    }


}