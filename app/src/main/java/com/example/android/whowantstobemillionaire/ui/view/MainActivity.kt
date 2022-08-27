package com.example.android.whowantstobemillionaire.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.whowantstobemillionaire.R

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