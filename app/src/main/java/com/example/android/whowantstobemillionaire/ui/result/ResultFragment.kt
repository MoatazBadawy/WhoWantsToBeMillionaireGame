package com.example.android.whowantstobemillionaire.ui.result

import androidx.navigation.Navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentResultBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {
    override fun onCreateView() {
        binding.backButton.setOnClickListener { v ->
            findNavController(v).navigateUp()
        }

        binding.playAgainButton.setOnClickListener { v ->
            findNavController(v).navigate(R.id.action_resultFragment_to_questionFragment)
            findNavController(v).popBackStack(R.id.questionFragment, false)
        }
    }
}