package com.example.twofa.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tencent.mmkv.MMKV
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import com.example.twofa.utils.EncryptUtil

class SecurityViewModel : ViewModel() {
    companion object {
        fun get(context: Context): SecurityViewModel? {
            (context as? FragmentActivity)?.apply {
                return ViewModelProvider(this).get(SecurityViewModel::class.java)
            }
            return null
        }

    }

    private val kv by lazy {
        MMKV.defaultMMKV()
    }

    private var _screenshotSelectState =
        mutableStateOf(kv.decodeBool("screenshotSelectState", false))
    val screenshotSelectState: State<Boolean> = _screenshotSelectState

    private var _pincodeSelectState = mutableStateOf(kv.decodeBool("pincodeSelectState", false))
    val pincodeSelectState: State<Boolean> = _pincodeSelectState

    private var _biometricsSelectState =
        mutableStateOf(kv.decodeBool("biometricsSelectState", true))
    val biometricsSelectState: State<Boolean> = _biometricsSelectState

    private val _pincode by lazy {
        mutableStateOf(EncryptUtil.getDecryptedData("pincode") ?: "")
    }
    val pincode: State<String> = _pincode

    var showConfirmToggleDialog = mutableStateOf(false)


    fun toggleScreenshotSelectState() {
        _screenshotSelectState.value = _screenshotSelectState.value.not()
        kv.encode("screenshotSelectState", screenshotSelectState.value)
    }

    fun togglePincodeSelectState() {
        _pincodeSelectState.value = _pincodeSelectState.value.not()
        kv.encode("pincodeSelectState", pincodeSelectState.value)
    }

    fun toggleBiometricsSelectState() {
        _biometricsSelectState.value = _biometricsSelectState.value.not()
        kv.encode("biometricsSelectState", biometricsSelectState.value)
    }

    fun changePin(pin: String) {
        _pincode.value = pin
        EncryptUtil.saveEncryptedData(pin, "pincode")
    }


}