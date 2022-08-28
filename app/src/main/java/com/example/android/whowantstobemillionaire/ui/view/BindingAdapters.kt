package com.example.android.whowantstobemillionaire.ui.view

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.util.statue.NetworkState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@BindingAdapter(value = ["app:displayLoadingState"])
fun <T>displayLoadingState(view: View, state: NetworkState<T>?){
    when(state){
        is NetworkState.Loading -> view.visibility = View.VISIBLE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter(value = ["app:displaySuccessState"])
fun displaySuccessState(view: TextView, state: NetworkState<QuizResponse>?){
    when(state){
        is NetworkState.Success -> {

            val list = state.data?.results
            if (list != null) {
                //timer(5,TimeUnit.SECONDS).map{ it -> list[it.toInt()]}.take(list.size.toLong())
                /*Observable.fromIterable(list).delay(1,TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        view.text = it.question
                    }*/
                view.text = list[0].question
            }
        }
        else -> {}
    }
}