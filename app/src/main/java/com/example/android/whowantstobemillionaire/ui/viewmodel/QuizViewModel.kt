package com.example.android.whowantstobemillionaire.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.util.statue.Resource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class QuizViewModel : ViewModel() {

    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()

    private val _quizResponse = MutableLiveData<Resource<QuizResponse?>>()

    fun getQuiz(difficulty: String): LiveData<Resource<QuizResponse?>> {
        _quizResponse.postValue(Resource.Loading)
        disposable.add(
            repository.executeQuizApi(difficulty)
                .subscribe(
                    { response ->
                        _quizResponse.postValue(Resource.Success(response))
                    },
                    { error ->
                        _quizResponse.postValue(
                            Resource.Error(
                                error.message ?: "Error While Fetching Data"
                            )
                        )
                    }
                ))
        return _quizResponse
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}