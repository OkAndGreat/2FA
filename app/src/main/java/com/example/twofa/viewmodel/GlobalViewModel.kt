package com.example.twofa.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class GlobalViewModel : ViewModel() {

    companion object {
        fun get(context: Context): GlobalViewModel? {
            (context as? ComponentActivity)?.apply {
                return ViewModelProvider(this).get(GlobalViewModel::class.java)
            }
            return null
        }

    }

    var navController: NavController? = null

}