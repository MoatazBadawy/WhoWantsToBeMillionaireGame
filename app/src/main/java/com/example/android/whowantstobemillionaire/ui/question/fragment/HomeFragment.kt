package com.example.android.whowantstobemillionaire.ui.question.fragment

import androidx.fragment.app.viewModels
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentQustionBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.question.viewmodel.QuizViewModel

class HomeFragment : BaseFragment<FragmentQustionBinding>(R.layout.fragment_qustion) {
    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreateView() {
    }
}