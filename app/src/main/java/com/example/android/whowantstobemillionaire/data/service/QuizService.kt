package com.example.android.whowantstobemillionaire.data.service

import com.example.android.whowantstobemillionaire.data.NetworkState
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {

    @GET("&")
    fun getQuiz(@Query("difficulty") difficulty:String): Response<QuizResponse>
}