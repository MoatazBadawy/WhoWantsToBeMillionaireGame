package com.example.android.whowantstobemillionaire.ui.lastresult

import androidx.core.view.children
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentLastResultBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.utils.helper.PrefForLastCoinsYouWin
import com.google.android.material.chip.Chip

class LastResultFragment :
    BaseFragment<FragmentLastResultBinding>
        (R.layout.fragment_last_result) {
    var shared = PrefForLastCoinsYouWin()

    override fun onCreateView() {
        binding.lifecycleOwner = viewLifecycleOwner
        loadLastResult()

        binding.startBtn.setOnClickListener { v ->
            Navigation.findNavController(v)
                .navigate(R.id.action_lastResultFragment_to_questionFragment)
        }
    }

    private fun loadLastResult() {
        val result = shared.loadLastResult(requireActivity())
        binding.chipGroup.children.forEach {
            val chip = it as Chip
            if (result == 5000 && chip.id == R.id.coins10) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 7500 && chip.id == R.id.coins9) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 10000 && chip.id == R.id.coins8) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 12500 && chip.id == R.id.coins7) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 15000 && chip.id == R.id.coins6) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 25000 && chip.id == R.id.coins5) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 50000 && chip.id == R.id.coins4) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 100000 && chip.id == R.id.coins3) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 250000 && chip.id == R.id.coins2) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 500000 && chip.id == R.id.coins1) chip.setChipBackgroundColorResource(R.color.golden_light)
            if (result == 1000000 && chip.id == R.id.coins) chip.setChipBackgroundColorResource(R.color.golden_light)
        }
    }
}