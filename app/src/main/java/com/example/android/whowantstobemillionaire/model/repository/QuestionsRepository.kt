package com.example.android.whowantstobemillionaire.model.repository

import com.example.android.whowantstobemillionaire.model.networking.API

class QuestionsRepository {
    fun getEasyQuestion(){
        val result= API.apiServi.getQuestion("easy")
    }

    fun getMeduimQuestion(){
        val result= API.apiServi.getQuestion("medium")
    }

    fun getHardQuestion(){
        val result= API.apiServi.getQuestion("hard")
    }
}