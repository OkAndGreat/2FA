package com.example.twofa.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tencent.mmkv.MMKV
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class SecurityViewModel : ViewModel() {
    companion object {
        fun get(context: Context): SecurityViewModel? {
            (context as? ComponentActivity)?.apply {
                return ViewModelProvider(this).get(SecurityViewModel::class.java)
            }
            return null
        }

    }

    private val kv by lazy {
        MMKV.defaultMMKV()
    }

    private var _screenshotSelectState = mutableStateOf(kv.decodeBool("screenshotSelectState"))
    val screenshotSelectState: State<Boolean> = _screenshotSelectState

    private var _pincode = mutableStateOf("")
    val pincode: State<String> = _pincode

    var showConfirmToggleDialog = mutableStateOf(false)


    fun toggleScreenshotSelectState() {
        _screenshotSelectState.value = _screenshotSelectState.value.not()
        kv.encode("screenshotSelectState", screenshotSelectState.value)
    }

    fun changePin(pin: String) {
        _pincode.value = pin
    }


}