package com.example.android.whowantstobemillionaire.data

sealed class NetworkState<out T>{
    data class Success<T>(val data :T?):NetworkState<T>()
    data class Error(val message:String):NetworkState<Nothing>()
    object Loading :NetworkState<Nothing>()
}
