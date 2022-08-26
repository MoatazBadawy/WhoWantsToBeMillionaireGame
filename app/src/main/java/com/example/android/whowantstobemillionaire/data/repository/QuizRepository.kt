package com.example.android.whowantstobemillionaire.data.repository

import com.example.android.whowantstobemillionaire.data.service.QuizRequestAPI
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class QuizRepository {

    fun getQuiz(difficulty: String) =
        getNetworkState { QuizRequestAPI.quizService.getQuiz(difficulty) }

    private fun <T> getNetworkState(function: () -> Response<T>): Observable<NetworkState<T>> {
        return Observable
            .create { state ->
                state.onNext(NetworkState.Loading)
                val result = function()
                if (result.isSuccessful) {
                    state.onNext(NetworkState.Success(result.body()))
                } else {
                    state.onNext(NetworkState.Error(result.message()))
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
