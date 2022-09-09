package com.example.android.whowantstobemillionaire.ui.question.adapter

import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.android.whowantstobemillionaire.R
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
        when (answerState) {
            AnswerState.CORRECT_ANSWER -> {
                if (selectedID == chip.id) {
                    chip.setChipBackgroundColorResource(R.color.green)
                }
            }

            AnswerState.WRONG_ANSWER -> {
                if (selectedID == chip.id) {
                    chip.setChipBackgroundColorResource(R.color.red)
                }
            }

            else -> {
                chip.setChipBackgroundColorResource(R.color.light_blue_black)
            }
        }

    }
    chipGroup.clearCheck()
}

@BindingAdapter(value = ["app:changeAnswerVisibility"])
fun changeAnswerVisibility(chip: Chip, answer: String?) {
    if (answer.isNullOrEmpty()) {
        chip.visibility = View.INVISIBLE
    } else {
        chip.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["htmlText"])
fun TextView.setHtmlText(string: String?) {
    text = HtmlCompat.fromHtml(string ?: "", HtmlCompat.FROM_HTML_MODE_COMPACT)
}