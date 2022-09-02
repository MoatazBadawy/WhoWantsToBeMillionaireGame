package com.example.android.whowantstobemillionaire.data.repository

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.request.QuizRequest
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class QuizRepository {

    private val quizRequest = QuizRequest.apiQuizService

    fun getQuizResult(difficulty: String): Observable<QuizResponse> {
        return quizRequest.getQuiz(difficulty = difficulty)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}