package com.example.android.whowantstobemillionaire.data.network

import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.utils.helper.Constants.QUESTION_TYPE
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizAPIService {
    @GET("api.php")
    fun getQuiz(
        @Query("amount") amount: Int,
        @Query("difficulty") difficulty: String,
        @Query("type") type: String = QUESTION_TYPE,
    ): Single<Response<QuizResponse>>
}