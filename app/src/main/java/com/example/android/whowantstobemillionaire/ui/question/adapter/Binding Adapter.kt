package com.example.android.whowantstobemillionaire.ui.question.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.android.whowantstobemillionaire.utils.state.State

@BindingAdapter(value = ["app:loadingState"])
fun <T> displayLoading(view: View, state: State<T>) {
    view.isVisible = (state is State.Loading)
}

@BindingAdapter(value = ["app:successState"])
fun <T> displaySuccess(view: View, state: State<T>) {
    view.isVisible = (state is State.Success)

}

@BindingAdapter(value = ["app:errorState"])
fun <T> displayError(view: View, state: State<T>) {
    view.isVisible = (state is State.Error)
}