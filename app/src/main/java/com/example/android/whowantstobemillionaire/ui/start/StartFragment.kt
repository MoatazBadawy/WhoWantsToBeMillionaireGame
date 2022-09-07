package com.example.android.whowantstobemillionaire.ui.start

import android.annotation.SuppressLint
import android.content.Context
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentStartBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment
import com.example.android.whowantstobemillionaire.utils.helper.PrefForLastCoinsYouWin

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {
    var shared= PrefForLastCoinsYouWin()
    @SuppressLint("SetTextI18n")
    override fun onCreateView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.startBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }

        binding.aboutBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_aboutFragment)
        }
        binding.resultBtn.setOnClickListener {
           val result= shared.loadLastResult(requireActivity())
            binding.resultBtn.text ="Last Coins You Won is ${result} $"
        }


    }




}