package com.example.android.whowantstobemillionaire.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentHomeBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val quizViewModel: QuizViewModel by viewModels()
    override fun setup() {
        binding.quizViewModel = quizViewModel
    }
}