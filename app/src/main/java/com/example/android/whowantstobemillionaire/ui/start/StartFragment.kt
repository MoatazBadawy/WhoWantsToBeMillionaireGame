package com.example.android.whowantstobemillionaire.ui.start

import android.annotation.SuppressLint
import android.content.Context
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentStartBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start) {

    override fun onCreateView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.startBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }

        binding.aboutBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_aboutFragment)
        }
        binding.resultBtn.setOnClickListener {
            loadLastResult()
        }


    }

    @SuppressLint("SetTextI18n")
    private fun loadLastResult() {
        val SharedPref = requireActivity().getSharedPreferences("SaveResult", Context.MODE_PRIVATE)
        val result = SharedPref.getInt("number", 0)
        binding.resultBtn.text = "last result = ${result}"
    }


}