package com.example.android.whowantstobemillionaire.util.z_old

class HomeFragmentPast
//    private fun getNewQuestion() {
//        when (quizViewModel.questionNumber) {
//            in 1..5 -> quizViewModel.getQuiz(Constants.EASY)
//            in 6..10 -> quizViewModel.getQuiz(Constants.MEDIUM)
//            in 11..15 -> quizViewModel.getQuiz(Constants.HARD)
//            15 -> navigateToResultsFragment()
//            else -> {
//                quizViewModel.getQuiz(Constants.EASY)
//                quizViewModel.questionNumber = 0
//            }
//        }
//    }

//    private fun checkSelectedAnswer() {
//        val selectedAnswer = binding.radioGroupAnswers.checkedRadioButtonId
//        if (selectedAnswer != -1) {
//            val radioButton = binding.radioGroupAnswers.findViewById<RadioButton>(selectedAnswer)
//            val index = binding.radioGroupAnswers.indexOfChild(radioButton)
//            if (correctAnswer(index)) {
//                navigateToHomeFragment()
//                getNewQuestion()
//            } else {
//                navigateToResultsFragment()
//            }
//        }
//    }

//    private fun navigateToStartFragment() {
//        Navigation.findNavController(requireView())
//            .navigate(R.id.action_homeFragment_to_startFragment)
//    }
//
//    private fun navigateToResultsFragment() {
//        Navigation.findNavController(requireView())
//            .navigate(R.id.action_homeFragment_to_resultFragment)
//    }
//
//    private fun navigateToHomeFragment() =
//        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_self)
//
//
//    override fun callback() {
//        binding.removeButton.setOnClickListener {
//            onRemove()
//        }
//
//        binding.progressBar.setOnProgressChangeListener { progress ->
//            onProgress(progress)
//        }
//
//        binding.replaceButton.setOnClickListener {
//            onReplace()
//        }
//
//        binding.buttonSubmit.setOnClickListener {
//            checkSelectedAnswer()
//        }
//
//        binding.imageQuit.setOnClickListener {
//            navigateToStartFragment()
//        }
//
//        binding.imageLevels.setOnClickListener {
//            navigateToResultsFragment()
//        }

//    }
//
//
//    private fun onReplace() {
//        if (countReplace < 1) {
//            count--
//            navigateToHomeFragment()
//            binding.replaceButton.disable()
//            isReplaced = true
//        }
//        countReplace++
//    }
//
//    private fun onProgress(progress: Float) {
//        if (progress == binding.progressBar.max) {
//            navigateToResultsFragment()
//            binding.progressBar.progressFromPrevious
//        }
//    }
//
//    private fun onRemove() {
//        if (countRemove < 1) {
//            removeTwoAnswers()
//            binding.removeButton.disable()
//            isRemoved = true
//        }
//        countRemove++
//    }
//
//    private fun removeTwoAnswers() {
//        var count = 0
//        for (i in 0..3) {
//            val radioButton = binding.radioGroupAnswers.getChildAt(i) as RadioButton
//            if (!correctAnswer(i)) {
//                radioButton.visibility = View.INVISIBLE
//                count++
//                if (count == 2) break
//            }
//        }
//    }
//
//    private fun setHelpMethodsButtons() {
//        if (isRemoved) {
//            binding.removeButton.disable()
//        }
//
//        if (isReplaced) {
//            binding.replaceButton.disable()
//        }
////    }
//}