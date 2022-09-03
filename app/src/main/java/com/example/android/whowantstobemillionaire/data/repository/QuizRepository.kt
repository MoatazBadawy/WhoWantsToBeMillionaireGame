package com.example.android.whowantstobemillionaire.data.repository

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.request.QuizRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class QuizRepository {
    private val quizRequest = QuizRequest.apiQuizService

    fun executeQuizApi(difficulty: String): Single<QuizResponse> {
        return quizRequest.getQuiz(difficulty = difficulty)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}