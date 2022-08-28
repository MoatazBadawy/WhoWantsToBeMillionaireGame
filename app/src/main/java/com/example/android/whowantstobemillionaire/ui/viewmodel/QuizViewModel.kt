package com.example.android.whowantstobemillionaire.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.util.helper.add
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

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
            repository.getQuiz(1,"multiple","easy")
                .subscribe {
                    _quizResponse.postValue(it)
                }//.add(disposable)
    }

//    fun looper(){
//        val observable = Observable
//            .just(getEasyQuiz())
//            .flatMap {
//                it.interval(5,TimeUnit.SECONDS)
//                .take(5)
//            }
//    }
//
    private fun getMediumQuiz() {
        for(i in 0..4){
            _quizResponse.postValue(NetworkState.Loading)
            repository.getQuiz(1,"multiple","medium")
                .subscribe {
                    _quizResponse.postValue(it)
                }.add(disposable)
        }
    }

    private fun getHardQuiz() {
        for(i in 0..4){
            _quizResponse.postValue(NetworkState.Loading)
            repository.getQuiz(1,"multiple","hard")
                .subscribe {
                    _quizResponse.postValue(it)
                }.add(disposable)
        }
    }

    init {
        getEasyQuiz()
        //getMediumQuiz()
        //getHardQuiz()
    }

    /*override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }*/
}