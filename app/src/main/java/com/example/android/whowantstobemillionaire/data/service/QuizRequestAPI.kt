package com.example.android.whowantstobemillionaire.data.service

import com.example.android.whowantstobemillionaire.util.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QuizRequestAPI {

    private val request = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val quizService = request.create(QuizService::class.java)

}