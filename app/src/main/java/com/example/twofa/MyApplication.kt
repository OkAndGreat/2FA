package com.example.twofa

import android.app.Application
import android.content.Context
import com.example.twofa.utils.LogUtil
import com.tencent.mmkv.MMKV

/**
 * 自定义 Application
 */

val appContext: Context = MyApplication.instance.applicationContext

class MyApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        val rootDir = MMKV.initialize(this)
        LogUtil.i(rootDir)
    }

}