package com.example.android.whowantstobemillionaire.ui.question.adapter

import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.utils.helper.Answer
import com.example.android.whowantstobemillionaire.utils.helper.AnswerState
import com.example.android.whowantstobemillionaire.utils.state.State
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

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

@BindingAdapter(value = ["app:changeBackgroundColor"])
fun changeBackgroundColor(chipGroup: ChipGroup, answerState: AnswerState?) {
    val selectedID = chipGroup.checkedChipId

    chipGroup.children.forEach { chip ->
        chip as Chip
        if (answerState == AnswerState.CORRECT_ANSWER && selectedID == chip.id) {
            chip.setChipBackgroundColorResource(R.color.success_answer)
        } else if (answerState == AnswerState.WRONG_ANSWER && selectedID == chip.id) {
            chip.setChipBackgroundColorResource(R.color.wrong_answer)
        } else {
            chip.setChipBackgroundColorResource(R.color.main_color)
        }
    }
    chipGroup.clearCheck()
}