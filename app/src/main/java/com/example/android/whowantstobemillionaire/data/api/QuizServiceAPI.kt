package com.example.android.whowantstobemillionaire.data.api

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizServiceAPI {
    @GET("api.php")
    fun getQuiz(@Query("amount") amount: Int = 1,
                @Query("type") type: String = "multiple",
                @Query("difficulty") difficulty: String
    ): Observable<QuizResponse>
}