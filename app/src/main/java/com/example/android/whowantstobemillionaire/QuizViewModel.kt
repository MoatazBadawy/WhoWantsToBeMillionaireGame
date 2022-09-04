package com.example.android.whowantstobemillionaire

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.state.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class QuizViewModel: ViewModel(){

    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()


    private val _quizResponse = MutableLiveData<State<QuizResponse?>>()
    val quizResponse: LiveData<State<QuizResponse?>>
        get() = _quizResponse

    private fun getQuestion(){
        repository.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ respons ->
                Log.i("AMEER","${respons.toData()?.quizzes}")
            } ,
                {
                    it.message?.let { it1 -> Log.i("AMEER", it1) }

                }

            )

    }

}