package com.example.twofa.viewmodel

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IOportViewModel : ViewModel() {

    companion object {
        fun get(context: Context): IOportViewModel? {
            (context as? FragmentActivity)?.apply {
                return ViewModelProvider(this)[IOportViewModel::class.java]
            }
            return null
        }

    }
}