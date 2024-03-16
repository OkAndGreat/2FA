package com.example.twofa.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SecurityViewModel : ViewModel() {
    companion object {
        fun get(context: Context): SecurityViewModel? {
            (context as? ComponentActivity)?.apply {
                return ViewModelProvider(this).get(SecurityViewModel::class.java)
            }
            return null
        }

    }
}