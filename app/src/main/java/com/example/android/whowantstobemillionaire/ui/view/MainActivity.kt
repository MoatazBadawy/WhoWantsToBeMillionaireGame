package com.example.android.whowantstobemillionaire.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.android.whowantstobemillionaire.R
import com.example.android.whowantstobemillionaire.ui.viewmodel.QuizViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // just for testing
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,HomeFragment())
            .commit()
    }
}