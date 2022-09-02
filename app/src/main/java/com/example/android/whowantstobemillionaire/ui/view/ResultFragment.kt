package com.example.android.whowantstobemillionaire.ui.view
import androidx.navigation.Navigation
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.databinding.FragmentResultBinding
import com.example.android.whowantstobemillionaire.ui.view.base.BaseFragment
import com.example.android.whowantstobemillionaire.util.count
import com.example.android.whowantstobemillionaire.util.quizCoins

class ResultFragment : BaseFragment<FragmentResultBinding>(R.layout.fragment_result) {
    val totalCorrectAnswers = count - 2
    val totalMoney = quizCoins[totalCorrectAnswers]
    override fun setup() {
        setData()
        binding.playAgain.setOnClickListener{
            navigateToStartFragment()
        }
    }
    private fun navigateToStartFragment() {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_resultFragment_to_startFragment)
    }
    private fun setData() {
        when(totalCorrectAnswers) {
            in 0..4 -> showFailedtState()
            in 5..14 -> showPassState()
            15 -> showMillionairState()
        }
    }
    private fun showFailedtState() {
        binding.lottieAnimationView.setAnimation(R.raw.loss_animation)
        "Unfortunately!".also { binding.primaryResultTextView.text = it }
        "You couldn't pass the challenge for this day".also { binding.secondaryResultTextView.text = it }
        ("$totalCorrectAnswers / 15").also { binding.totalCorrectAnswers.text = it }
        binding.earnedMoney.text = "0"
    }
    private fun showPassState() {
        binding.lottieAnimationView.setAnimation(R.raw.pass_animation)
        "Congratulation!".also { binding.primaryResultTextView.text = it }
        "You passed the challenge for this day".also { binding.secondaryResultTextView.text = it }
        ("$totalCorrectAnswers / 15").also { binding.totalCorrectAnswers.text = it }
        if(totalCorrectAnswers <= 9){
            binding.earnedMoney.text = "5000"
        }
        else {
            binding.earnedMoney.text = "25000"
        }
    }
    private fun showMillionairState() {
        binding.lottieAnimationView.setAnimation(R.raw.winning_animation)
        "Congratulation!".also { binding.primaryResultTextView.text = it }
        "You won 1 million dollars for the challenge for this day".also { binding.secondaryResultTextView.text = it }
        ("${totalCorrectAnswers + 1} / 15").also { binding.totalCorrectAnswers.text = it }
        binding.earnedMoney.text =  quizCoins[totalCorrectAnswers+1].toString()
    }
    override fun callback() {
    }
}