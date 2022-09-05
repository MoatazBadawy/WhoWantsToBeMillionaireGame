package com.example.android.whowantstobemillionaire.ui.question.fragment

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

        quizViewModel.losingNavigate.observe(
            viewLifecycleOwner
        ) {
            if (it) navigateToLosingFragment()
        }
    }

    fun navigateToLosingFragment() {
        requireView().findNavController().navigate(R.id.action_homeFragment_to_losingFragment)
    }
}