package com.example.android.whowantstobemillionaire.ui.question.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.state.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class QuestionViewModel : ViewModel() {

    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()

    private val _questionsList = MutableLiveData<State<List<Quiz>?>>()
    val questionsList: LiveData<State<List<Quiz>?>> = _questionsList

    private fun getQuiz() {
        _questionsList.postValue(State.Loading)
        disposable.add(
            repository.getAllQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _questionsList.postValue(State.Success(it.toData()?.quizzes))
                    Log.v("QuestionViewModel", it.toData().toString())
                }, {
                    _questionsList.postValue(State.Error(it.message ?: "Error while fetching data"))
                })
        )
    }

    init {
        getQuiz()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}