package com.example.android.whowantstobemillionaire.data.repository

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.network.API
import com.example.android.whowantstobemillionaire.utils.helper.Constants
import com.example.android.whowantstobemillionaire.utils.helper.Constants.MAX_QUESTION_NUMBER
import com.example.android.whowantstobemillionaire.utils.helper.DifficultyLevels
import com.example.android.whowantstobemillionaire.utils.state.State
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class QuizRepository {
    fun getAllQuestions(): Observable<State<QuizResponse>> {
        return wrapperWithState {
            callQuestion(DifficultyLevels.EASY.level).subscribeOn(Schedulers.io())
                .zipWith(callQuestion(DifficultyLevels.MEDIUM.level).subscribeOn(Schedulers.io()),
                    BiFunction { firstResponse: Response<QuizResponse>,
                                 secondResponse: Response<QuizResponse> ->
                        combineResult(firstResponse, secondResponse)
                    })
                .zipWith(
                    callQuestion(DifficultyLevels.HARD.level)
                ) { firstResponse: Response<QuizResponse>,
                    secondResponse: Response<QuizResponse> ->
                    combineResult(firstResponse, secondResponse)
                }.toObservable()
        }
    }

    private fun callQuestion(difficulty: String): Single<Response<QuizResponse>> {
        return API.apiQuizService.getQuiz(amount = MAX_QUESTION_NUMBER, difficulty = difficulty)
    }

    private fun combineResult(
        firstResponse: Response<QuizResponse>,
        secondResponse: Response<QuizResponse>
    ): Response<QuizResponse> {
        firstResponse.body().apply {
            secondResponse.body()?.quizzes?.let {
                this?.quizzes?.addAll(it)
            }
        }
        return firstResponse
    }

    private fun <T> wrapperWithState(function: () -> Observable<Response<T>>): Observable<State<T>> {
        return function().map {
            if (it.isSuccessful) {
                State.Success(it.body())
            } else {
                State.Error(it.message())
            }
        }
    }
}