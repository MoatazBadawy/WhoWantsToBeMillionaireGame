package com.example.android.whowantstobemillionaire.ui.view.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.databinding.FragmentQustionBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel
import com.example.android.whowantstobemillionaire.util.helper.Constants
import com.example.android.whowantstobemillionaire.util.statue.Resource

class QuestionFragment : BaseFragment<FragmentQustionBinding>(R.layout.fragment_qustion) {
    private val quizViewModel: QuizViewModel by viewModels()
    var count = 1
    override fun onCreateView() {
        getQuestion()
        getNewQuestion()
    }

    private fun getQuestion() {
        quizViewModel.quizResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                //    binding.animationLoading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                 //   binding.animationLoading.visibility = View.GONE
                    it.data?.let { data ->
                        count++
                        setQuestion(data)
                    }

                }
                is Resource.Error -> {
                  //  binding.animationLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun setQuestion(data: QuizResponse) {
//        binding.textQuestion.text = data.quizzes?.get(0)!!.question
//        val answers: MutableList<String> = getAnswers(data.quizzes!!)
//        for (i in 0..3) {
//            binding.radioAnswerOne.text = answers[0]
//            binding.radioAnswerTwo.text = answers[1]
//            binding.radioAnswerThree.text = answers[2]
//            binding.radioAnswerFour.text = answers[3]
//        }
    }

    private fun getAnswers(list: List<Quiz>): MutableList<String> {
        val mutableList = list[0].incorrectAnswers?.toMutableList()
        mutableList?.add(list[0].correctAnswer.toString())
        mutableList?.shuffle()
        return mutableList!!
    }

    private fun getNewQuestion() {
        when (count) {
            in 1..5 -> quizViewModel.getQuiz(Constants.EASY)
            in 6..10 -> quizViewModel.getQuiz(Constants.MEDIUM)
            in 11..15 -> quizViewModel.getQuiz(Constants.HARD)
            16 -> navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() =
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_self)

    override fun callback() {
//        binding.buttonSubmit.setOnClickListener {
//            navigateToHomeFragment()
        }
    }
