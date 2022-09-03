package com.example.android.whowantstobemillionaire.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentStartBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment

class AboutFragment : BaseFragment<FragmentStartBinding>(R.layout.fragment_about) {
    override fun onCreateView() {

    }

    override fun callback() {
        binding.startBtn.setOnClickListener { v ->
            Navigation.findNavController(v).navigate(R.id.action_startFragment_to_homeFragment)
        }
    }
}