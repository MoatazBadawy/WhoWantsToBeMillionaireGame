package com.example.android.whowantstobemillionaire.model.repository

import com.example.android.whowantstobemillionaire.model.networking.API
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class QuestionsRepository {
    fun getEasyQuestion(){
         API.apiServi.getQuestion("easy")
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe (
                 {

                 },
                 {

                 }
             )
    }

    fun getMeduimQuestion(){
        val result= API.apiServi.getQuestion("medium")
    }

    fun getHardQuestion(){
        val result= API.apiServi.getQuestion("hard")
    }
}