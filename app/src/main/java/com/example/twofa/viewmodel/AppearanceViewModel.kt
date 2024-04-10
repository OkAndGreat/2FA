package com.example.twofa.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tencent.mmkv.MMKV

class AppearanceViewModel : ViewModel() {

    companion object {
        fun get(context: Context): AppearanceViewModel? {
            (context as? FragmentActivity)?.apply {
                return ViewModelProvider(this)[AppearanceViewModel::class.java]
            }
            return null
        }
    }

    private val kv by lazy {
        MMKV.defaultMMKV()
    }

    private var _showNextTokenSelectState =
        mutableStateOf(kv.decodeBool("showNextTokenSelectState", false))
    val showNextTokenSelectState: State<Boolean> = _showNextTokenSelectState

    private var _hideTokenSelectState = mutableStateOf(kv.decodeBool("hideTokenSelectState", false))
    val hideTokenSelectState: State<Boolean> = _hideTokenSelectState

    fun toggleShowNextTokenSelectState() {
        _showNextTokenSelectState.value = _showNextTokenSelectState.value.not()
        kv.encode("showNextTokenSelectState", _showNextTokenSelectState.value)
    }

    fun toggleHideTokenSelectState() {
        _hideTokenSelectState.value = _hideTokenSelectState.value.not()
        kv.encode("hideTokenSelectState", _hideTokenSelectState.value)
    }


}