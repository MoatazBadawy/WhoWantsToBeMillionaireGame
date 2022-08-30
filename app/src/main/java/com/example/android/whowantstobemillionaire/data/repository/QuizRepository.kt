package com.example.android.whowantstobemillionaire.data.repository

import android.view.View
import android.widget.RadioButton
import com.example.android.whowantstobemillionaire.data.service.QuizRequestAPI
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import java.util.concurrent.TimeUnit

class QuizRepository {

    fun getQuiz(amount: Int, type: String, difficulty: String) =
        getNetworkState { QuizRequestAPI.quizService.getQuiz(amount, type, difficulty) }

    private fun <T> getNetworkState(function: () -> Observable<Response<T>>): Observable<NetworkState<T>> {

        return Observable
            /*.intervalRange(0,5,0,2,TimeUnit.SECONDS)
            .flatMap { return@flatMap Observable*/.create { state ->
                state.onNext(NetworkState.Loading)
                val result = function()
                result.subscribe {
                    if (it.isSuccessful) {
                        state.onNext(NetworkState.Success(it.body()))
                    } else {
                        state.onNext(NetworkState.Error(it.message()))
                    }
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) }

    }