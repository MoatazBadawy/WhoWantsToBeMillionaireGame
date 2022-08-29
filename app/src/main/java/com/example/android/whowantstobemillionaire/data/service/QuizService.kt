package com.example.android.whowantstobemillionaire.data.service

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuizService {
    @GET("api.php")
    fun getQuiz(@Query("amount") amount: Int,
                @Query("type") type: String,
                @Query("difficulty") difficulty: String
    ): Observable<Response<QuizResponse>>

}