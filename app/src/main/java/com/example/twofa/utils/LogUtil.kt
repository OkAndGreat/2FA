package com.example.twofa.utils

import android.util.Log

/**
 * @author:ztaiwang
 * @date:2023/8/10
 */
object LogUtil {

    const val TAG = "wzt"

    // 控制是否要输出log
    private var sIsRelease = false

    fun d(content: String) {
        if (!sIsRelease) {
            Log.d(TAG, content)
        }
    }

    fun v(content: String) {
        if (!sIsRelease) {
            Log.d(TAG, content)
        }
    }

    fun i(content: String) {
        if (!sIsRelease) {
            Log.d(TAG, content)
        }
    }

    fun w(content: String) {
        if (!sIsRelease) {
            Log.d(TAG, content)
        }
    }

    fun e(content: String) {
        if (!sIsRelease) {
            Log.d(TAG, content)
        }
    }
}