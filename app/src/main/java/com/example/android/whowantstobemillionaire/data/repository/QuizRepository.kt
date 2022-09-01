package com.example.android.whowantstobemillionaire.data.repository

import com.example.android.whowantstobemillionaire.data.request.QuizRequest
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response

class QuizRepository {
    fun getQuiz(difficulty: String) =
        getNetworkState { QuizRequest.apiQuizService.getQuiz(difficulty = difficulty) }

    private fun <T> getNetworkState(getResponse: () -> Observable<Response<T>>): Observable<NetworkState<T>> {
        return Observable.create { state ->
                state.onNext(NetworkState.Loading)
                val result = getResponse()
                result.subscribe {
                    if (it.isSuccessful) {
                        state.onNext(NetworkState.Success(it.body()))
                    } else {
                        state.onNext(NetworkState.Error(it.message()))
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}