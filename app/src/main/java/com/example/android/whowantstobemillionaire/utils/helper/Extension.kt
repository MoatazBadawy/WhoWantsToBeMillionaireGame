package com.example.android.whowantstobemillionaire.utils.helper

import android.widget.Button
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.add(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun Button.disable(){
    this.apply {
        alpha = 0.5f
        isEnabled = false
    }
}