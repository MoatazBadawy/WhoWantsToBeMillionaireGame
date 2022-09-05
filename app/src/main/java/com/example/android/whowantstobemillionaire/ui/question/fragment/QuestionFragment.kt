package com.example.android.whowantstobemillionaire.ui.question.fragment

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentQustionBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.question.viewmodel.QuestionViewModel

class QuestionFragment : BaseFragment<FragmentQustionBinding>(R.layout.fragment_qustion) {
    private val quizViewModel: QuestionViewModel by viewModels()
    override fun onCreateView() {
        binding.questionViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        quizViewModel.losingNavigate.observe(viewLifecycleOwner) { if (it) navigateToLosingFragment() }

        saveLastResult()
    }
    private fun saveLastResult() {
        val sharedPreferences= requireActivity().getSharedPreferences("SaveResult", Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        quizViewModel.numberOfQuestion.observe(viewLifecycleOwner){
            val result=it.minus(1)
            editor.putInt("number",result)
            editor.apply()
        }
    }

    fun navigateToLosingFragment() {
        requireView().findNavController().navigate(R.id.action_homeFragment_to_losingFragment)
    }
}