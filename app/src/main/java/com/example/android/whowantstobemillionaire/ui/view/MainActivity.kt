package com.example.android.whowantstobemillionaire.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.android.whowantstobemillionaire.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding? = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //navigateTo(HomeFragment())
    }
}

private fun FragmentActivity.navigateTo(to: Fragment, bundle: Bundle? = null){
    to.arguments = bundle
    changeNavigation(this, NavigationType.Add, to)
}

private fun changeNavigation(activity: FragmentActivity, state: NavigationType, to: Fragment){
    val transaction = activity.supportFragmentManager.beginTransaction()
    when(state){
        NavigationType.Add -> transaction.add(R.id.fragment_container, to)
        NavigationType.Remove -> transaction.remove(to)
        NavigationType.Replace -> transaction.replace(R.id.fragment_container, to)
    }
    transaction.addToBackStack(null).commit()
}

enum class NavigationType {
    Add,
    Remove,
    Replace
}