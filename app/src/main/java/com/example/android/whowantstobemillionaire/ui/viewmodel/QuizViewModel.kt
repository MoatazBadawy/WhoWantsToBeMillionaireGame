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

    private val _quizResponse = MutableLiveData<NetworkState<QuizResponse?>>()
    val quizResponse: LiveData<NetworkState<QuizResponse?>>
        get() = _quizResponse

    fun getQuiz(difficulty: String) {
        _quizResponse.postValue(NetworkState.Loading)
        disposable.add(
            repository.getQuizResult(difficulty)
                .subscribe(
                    { response ->
                        _quizResponse.postValue(NetworkState.Success(response))
                    },
                    { error ->
                        _quizResponse.postValue(
                            NetworkState.Error(
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