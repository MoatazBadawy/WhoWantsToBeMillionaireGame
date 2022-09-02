package com.example.android.whowantstobemillionaire.ui.view

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentStartBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment

class StartFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_start){
    override fun setup() {
        (requireActivity()as AppCompatActivity).supportActionBar?.hide()

    }

    override fun callback() {
        binding.startBtn.setOnClickListener{ v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }
    }
}