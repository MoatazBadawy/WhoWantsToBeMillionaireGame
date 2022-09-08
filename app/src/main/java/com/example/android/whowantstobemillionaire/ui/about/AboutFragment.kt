package com.example.android.whowantstobemillionaire.ui.about

import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentAboutBinding
import com.example.android.whowantstobemillionaire.ui.base.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding>(R.layout.fragment_about) {
    override fun onCreateView() {
        binding.backButton.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }
}