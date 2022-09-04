package com.example.android.whowantstobemillionaire.ui.question.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.State
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuestionViewModel : ViewModel() {

    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()

    private val _quizResponse = MutableLiveData<State<QuizResponse?>>()
    val quizResponse: LiveData<State<QuizResponse?>>
        get() = _quizResponse

    fun getQuiz() {
        _quizResponse.postValue(State.Loading)
        disposable.add(
            repository.getAllQuestions()
                .subscribe(
                    { response ->
                        _quizResponse.postValue(State.Success(response.toData()))
                    },
                    { error ->
                        _quizResponse.postValue(
                            State.Error(
                                error.message ?: "Error While Fetching Data"
                            )
                        )
                    }
                ))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}