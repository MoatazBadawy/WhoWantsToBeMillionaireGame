package com.example.android.whowantstobemillionaire.data.model

import com.google.gson.annotations.SerializedName

data class Quiz(
    @SerializedName("category")
    var category: String?,
    @SerializedName("correct_answer")
    var correctAnswer: String?,
    @SerializedName("difficulty")
    var difficulty: String?,
    @SerializedName("incorrect_answers")
    var incorrectAnswers: List<String>?,
    @SerializedName("question")
    var question: String?,
    @SerializedName("type")
    var type: String?
)