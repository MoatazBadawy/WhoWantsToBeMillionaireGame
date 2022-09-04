package com.example.android.whowantstobemillionaire.utils

import android.widget.ImageButton
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.add(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun ImageButton.disable(){
    this.apply {
        alpha = 0.5f
        isEnabled = false
    }
}