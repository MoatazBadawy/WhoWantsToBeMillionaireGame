package com.example.android.whowantstobemillionaire.ui.view

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.model.Result
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import com.skydoves.progressview.ProgressView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@BindingAdapter(value = ["app:displayLoadingState"])
fun <T> displayLoadingState(view: View, state: NetworkState<T>?) {
    when (state) {
        is NetworkState.Loading -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

var listOfAnswers = listOf<String>()
var checkedAnswers = mutableListOf<Boolean>()

@BindingAdapter(value = ["app:displaySuccessState"])
fun displaySuccessState(view: TextView, state: NetworkState<QuizResponse>?) {
    when (state) {
        is NetworkState.Success -> {
            val list = state.data?.results
            if (list != null) {
                view.text = list[0].question
            }
        }
        else -> {}
    }
}

@BindingAdapter(value = ["app:displayAnswer"])
fun displayUsingRadioGroup(view: RadioGroup, state: NetworkState<QuizResponse>?) {
    when (state) {
        is NetworkState.Success -> {
            val list = state.data?.results
            if (list != null) {
                listOfAnswers = getRandomAnswers(list)
                for (i in 0..3) {
                    val radioButton = view.getChildAt(i) as RadioButton
                    radioButton.text = listOfAnswers[i]
                    checkedAnswers.add(listOfAnswers[i] == list[0].correctAnswer)
                }
            }
        }
        else -> {}
    }
}

fun getRandomAnswers(list: List<Result>): List<String> {
    var mutableList = list[0].incorrectAnswers.toMutableList()
    mutableList.add(list[0].correctAnswer)
    mutableList.shuffle()
    return mutableList
}

fun trueAnswer(index: Int): Boolean {
    return checkedAnswers[index]
}


@BindingAdapter(value = ["app:timerSetting"])
fun handelProgressBar(progressView: ProgressView, state: NetworkState<QuizResponse>?) {

    when (state) {
        is NetworkState.Success -> {
            val list = state.data?.results
            if (list != null) {
                val observable = Observable.intervalRange(1, 30, 0, 1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                observable.subscribe {
                    progressView.progress = it.toFloat()
                    progressView.labelText = "$it sec"

                }

            }
        }
        else -> {}
    }
}





