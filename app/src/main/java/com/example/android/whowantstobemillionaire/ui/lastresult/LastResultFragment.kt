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
    private var shared = PrefForLastCoinsYouWin()

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
            when (result) {
                5000 -> goldenLight(chip, R.id.coins10)
                7500 -> goldenLight(chip, R.id.coins9)
                10000 -> goldenLight(chip, R.id.coins8)
                12500 -> goldenLight(chip, R.id.coins7)
                15000 -> goldenLight(chip, R.id.coins6)
                25000 -> goldenLight(chip, R.id.coins5)
                50000 -> goldenLight(chip, R.id.coins4)
                100000 -> goldenLight(chip, R.id.coins3)
                250000 -> goldenLight(chip, R.id.coins2)
                500000 -> goldenLight(chip, R.id.coins1)
                1000000 -> goldenLight(chip, R.id.coins)
            }
        }
    }

    private fun goldenLight(chip: Chip, id: Int) {
        if (chip.id == id)
            chip.setChipBackgroundColorResource(R.color.golden_light)
    }
}