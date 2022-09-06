package com.example.android.whowantstobemillionaire.ui.question.fragment

import android.app.AlertDialog
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

        binding.exitButton.setOnClickListener { v ->
            backQuizAlertDialog()
        }

        quizViewModel.losingNavigate.observe(
            viewLifecycleOwner
        ) {
            if (it) navigateToLosingFragment()
        }

        quizViewModel.resultNavigate.observe(
            viewLifecycleOwner
        ) {
            if (it) navigateToResultFragment()
        }
    }

    private fun navigateToLosingFragment() {
        requireView().findNavController().navigate(R.id.action_questionFragment_to_losingFragment)
    }

    private fun navigateToResultFragment() {
        requireView().findNavController().navigate(R.id.action_questionFragment_to_resultFragment)
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
}