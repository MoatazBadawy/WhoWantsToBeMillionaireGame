package com.example.android.whowantstobemillionaire.data.request

import com.example.android.whowantstobemillionaire.data.api.QuizAPIService
import com.example.android.whowantstobemillionaire.util.helper.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object QuizRequest {

    private fun myHttpClient(): OkHttpClient {
        val builder = OkHttpClient()
            .newBuilder()
            .callTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
        return builder.build()
    }

    private val request = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(myHttpClient())
        .build()

    val apiQuizService: QuizAPIService by lazy {
        request.create(QuizAPIService::class.java)
    }
}