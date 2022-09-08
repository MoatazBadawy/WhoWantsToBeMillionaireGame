package com.example.android.whowantstobemillionaire.utils.helper

import android.annotation.SuppressLint
import android.content.Context

class PrefForLastCoinsYouWin {
    companion object {
        private const val MY_PREFERENCE_NAME = "SaveResult"
        private const val PREF_TOTAL_KEY = "coins"
    }

    fun saveLastResult(context: Context, coins: Int) {
        val sharedPreferences =
            context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(PREF_TOTAL_KEY, coins)
        editor.apply()
    }

    @SuppressLint("SetTextI18n")
    fun loadLastResult(context: Context): Int {
        val sharedPref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPref.getInt(PREF_TOTAL_KEY, 0)
    }
}
