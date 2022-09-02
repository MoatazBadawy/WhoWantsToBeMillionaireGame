package com.example.android.whowantstobemillionaire.data.request

import com.example.android.whowantstobemillionaire.data.api.QuizServiceAPI
import com.example.android.whowantstobemillionaire.util.helper.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuizRequest {
    private val request = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val apiQuizService: QuizServiceAPI by lazy { request.create(QuizServiceAPI::class.java) }
}