package com.example.android.whowantstobemillionaire.ui.view.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.databinding.FragmentHomeBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel
import com.example.android.whowantstobemillionaire.util.*
import com.example.android.whowantstobemillionaire.util.helper.Constants
import com.example.android.whowantstobemillionaire.util.statue.Resource

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreateView() {
        getQuestion()
    }

    private fun getQuestion() {
        quizViewModel.getQuiz(Constants.EASY).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.animationLoading.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.animationLoading.visibility = View.GONE
                    it.data?.let { data ->
                        setQuestion(data)
                    }

                }
                is Resource.Error -> {
                    binding.animationLoading.visibility = View.GONE
                }
            }
        }
    }

    private fun setQuestion(data: QuizResponse) {
        binding.textQuestion.text = data.quizzes?.get(0)!!.question
        val answers: MutableList<String> = getAnswers(data.quizzes!!)
        for (i in 0..3) {
            binding.radioAnswerOne.text = answers[0]
            binding.radioAnswerTwo.text = answers[1]
            binding.radioAnswerThree.text = answers[2]
            binding.radioAnswerFour.text = answers[3]
        }
    }

    private fun getAnswers(list: List<Quiz>): MutableList<String> {
        val mutableList = list[0].incorrectAnswers?.toMutableList()
        mutableList?.add(list[0].correctAnswer.toString())
        mutableList?.shuffle()
        return mutableList!!
    }

    override fun callback() {
    }
}