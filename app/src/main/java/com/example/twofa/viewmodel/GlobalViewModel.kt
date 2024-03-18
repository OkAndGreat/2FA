package com.example.twofa.viewmodel

import android.content.Context
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController

class GlobalViewModel : ViewModel() {

    companion object {
        fun get(context: Context): GlobalViewModel? {
            (context as? FragmentActivity)?.apply {
                return ViewModelProvider(this).get(GlobalViewModel::class.java)
            }
            return null
        }

    }

    var navController: NavController? = null

    var localView: View? = null

}