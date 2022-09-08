package com.example.android.whowantstobemillionaire.ui.question.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.icu.text.CaseMap
import android.opengl.Matrix.length
import android.os.Message
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentQustionBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.question.viewmodel.QuestionViewModel
import com.example.android.whowantstobemillionaire.utils.helper.disable

class QuestionFragment : BaseFragment<FragmentQustionBinding>(R.layout.fragment_qustion) {
    private val quizViewModel: QuestionViewModel by viewModels()

    override fun onCreateView() {
        binding.questionViewModel = quizViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        quizViewModel.leaveQuestion.observe(
            viewLifecycleOwner
        ) {
            if (it) backQuizAlertDialog()
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

        quizViewModel.clickOnce.observe(
            viewLifecycleOwner
        ) {
            if (it) changeQuestionHelperOnClick()
        }
    }

    private fun navigateToLosingFragment() {
        requireView().findNavController().navigate(R.id.action_questionFragment_to_losingFragment)
    }

    private fun navigateToResultFragment() {
        requireView().findNavController().navigate(R.id.action_questionFragment_to_resultFragment)
    }

    private fun changeQuestionHelperOnClick() {
        binding.changeQuestion.disable()
    }

    private fun backQuizAlertDialog() {
        val dialog = AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
        dialog.apply {

            val message = SpannableString("Do you really want to withdraw from the competition?")
            message.setSpan(
                ForegroundColorSpan(Color.WHITE),0,message.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val title = SpannableString("Exit")
            title.setSpan(
                ForegroundColorSpan(Color.WHITE),0,title.length,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setTitle(title)
            setMessage(message).
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