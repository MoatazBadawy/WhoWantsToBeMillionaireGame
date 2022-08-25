package com.example.android.whowantstobemillionaire.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _easyQuizResponse.postValue(it)
            }, {
                _easyQuizResponse.postValue(it.message)
            }).isDisposed
    }

    private fun getMediumQuiz() {
        _easyQuizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("medium")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _mediumQuizResponse.postValue(it)
            }, {
                _mediumQuizResponse.postValue(NetworkState.Error(it.message))
            }).isDisposed
    }

    private fun getHardQuiz() {
        _easyQuizResponse.postValue(NetworkState.Loading)
        repository.getQuiz("hard")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _hardQuizResponse.postValue(it)
            }, {
                _hardQuizResponse.postValue(NetworkState.Error(it.message))
            }).isDisposed
    }

    init {
        getEasyQuiz()
        getMediumQuiz()
        getHardQuiz()
    }
}