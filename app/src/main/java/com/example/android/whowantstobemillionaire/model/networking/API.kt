package com.example.android.whowantstobemillionaire.model.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private val BASE_URL ="https://opentdb.com/api.php?"
    private val retrofite = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiServi = retrofite.create(IQuestionsService::class.java)

}