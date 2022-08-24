package com.example.android.whowantstobemillionaire.model.response


import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("response_code")
    var responseCode: Int,
    @SerializedName("results")
    var results: List<Result>
)