package com.example.android.whowantstobemillionaire.ui.question.fragment

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
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


    fun backQuizAlertDialog(context: Context){
        val dialog = AlertDialog.Builder(context)
        dialog.apply {
            setTitle("انسحاب")
            setMessage("تاكيد الانسحاب")
            setPositiveButton("yes") { _, _ ->
                requireView().findNavController().navigate(R.id.action_homeFragment_to_resultFragment)
            }
            setNegativeButton("No") { it, _ ->
                it.cancel()
            }
        }
        dialog.show()
    }

}