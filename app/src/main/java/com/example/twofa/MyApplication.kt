package com.example.twofa

import android.app.Application
import android.content.Context

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

}