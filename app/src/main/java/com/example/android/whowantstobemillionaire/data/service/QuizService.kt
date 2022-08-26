package com.example.android.whowantstobemillionaire.data.service

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {

    @GET("&")
    fun getQuiz(@Query("difficulty") difficulty:String): Response<QuizResponse>
}