package com.example.android.whowantstobemillionaire.model.networking

import com.example.android.whowantstobemillionaire.model.response.QuestionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IQuestionsService {

    @GET("amount=5&")
    fun getQuestion(@Query("difficulty") difficulty:String): Response<QuestionsResponse>
}