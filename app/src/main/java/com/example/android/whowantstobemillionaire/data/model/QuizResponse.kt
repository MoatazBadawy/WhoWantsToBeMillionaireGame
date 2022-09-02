package com.example.android.whowantstobemillionaire.data.model

import com.google.gson.annotations.SerializedName

data class QuizResponse(
    @SerializedName("response_code")
    var responseCode: Int?,
    @SerializedName("results")
    var quizzes: List<Quiz>?,
)