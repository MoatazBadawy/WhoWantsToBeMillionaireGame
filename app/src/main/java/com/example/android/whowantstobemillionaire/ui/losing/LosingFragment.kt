package com.example.android.whowantstobemillionaire.ui.losing

import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentLosingBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment

class LosingFragment : BaseFragment<FragmentLosingBinding>(R.layout.fragment_losing) {
    override fun onCreateView() {
        binding.backButton.setOnClickListener { v ->
            findNavController(v).navigate(R.id.action_losingFragment_to_startFragment)
            findNavController(v).navigate(R.id.startFragment)
        }

        binding.playAgainButton.setOnClickListener { v ->
            findNavController(v).navigate(R.id.action_losingFragment_to_questionFragment)
            findNavController(v).popBackStack(R.id.questionFragment, false)
        }
    }
}