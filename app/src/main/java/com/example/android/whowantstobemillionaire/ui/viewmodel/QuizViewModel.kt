package com.example.android.whowantstobemillionaire.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.util.statue.NetworkState

class QuizViewModel : ViewModel() {
    private val repository = QuizRepository()

    private val _easyQuizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val easyQuizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _easyQuizResponse

    private val _mediumQuizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val mediumQuizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _mediumQuizResponse

    private val _hardQuizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val hardQuizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _hardQuizResponse

    private fun getEasyQuiz() {
        _easyQuizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("easy")
            .subscribe {
                _easyQuizResponse.postValue(it)
            }.isDisposed
    }

    private fun getMediumQuiz() {
        _easyQuizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("medium")
            .subscribe {
                _mediumQuizResponse.postValue(it)
            }.isDisposed
    }

    private fun getHardQuiz() {
        _easyQuizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("hard")
            .subscribe {
                _hardQuizResponse.postValue(it)
            }.isDisposed
    }

    init {
        getEasyQuiz()
        getMediumQuiz()
        getHardQuiz()
    }
}