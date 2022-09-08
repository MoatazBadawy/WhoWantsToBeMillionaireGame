package com.example.android.whowantstobemillionaire.ui.result

import android.annotation.SuppressLint
import androidx.navigation.Navigation.findNavController
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentResultBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.utils.helper.PrefForLastCoinsYouWin

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {
    private var shared = PrefForLastCoinsYouWin()

    override fun onCreateView() {
        loadLastResult()
        binding.backButton.setOnClickListener { v ->
            findNavController(v).navigate(R.id.action_resultFragment_to_startFragment)
            findNavController(v).popBackStack(R.id.startFragment, false)
        }

        binding.playAgainButton.setOnClickListener { v ->
            findNavController(v).navigate(R.id.action_resultFragment_to_questionFragment)
            findNavController(v).popBackStack(R.id.questionFragment, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadLastResult() {
        val result = shared.loadLastResult(requireActivity())
        binding.yourCoins.text = "$result $"
    }
}