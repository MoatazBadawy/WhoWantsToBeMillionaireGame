package com.example.android.whowantstobemillionaire.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.util.helper.add
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuizViewModel : ViewModel() {
    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()

    /*private val _easyQuizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val easyQuizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _easyQuizResponse

    private val _mediumQuizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val mediumQuizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _mediumQuizResponse

    private val _hardQuizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val hardQuizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _hardQuizResponse*/

    private val _quizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val quizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _quizResponse

    private fun getEasyQuiz() {
        _quizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("easy")
            .subscribe {
                _quizResponse.postValue(it)
            }.add(disposable)
    }

    private fun getMediumQuiz() {
        _quizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("medium")
            .subscribe {
                _quizResponse.postValue(it)
            }.add(disposable)
    }

    private fun getHardQuiz() {
        _quizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("hard")
            .subscribe {
                _quizResponse.postValue(it)
            }.add(disposable)
    }

    init {
        getEasyQuiz()
        getMediumQuiz()
        getHardQuiz()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}