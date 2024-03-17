package com.example.twofa

import android.app.Application
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.twofa.utils.LogUtil
import com.tencent.mmkv.MMKV
import java.security.KeyStore
import javax.crypto.KeyGenerator


/**
 * 自定义 Application
 */

val appContext: Context = MyApplication.instance.applicationContext

class MyApplication : Application() {

    companion object {
        lateinit var instance: Application

        const val KEY_ALIAS = "myKeyAlias"
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        val rootDir = MMKV.initialize(this)
        LogUtil.i(rootDir)
        initKeyStore()
    }

    private fun initKeyStore() {
        try {
            // 获取AndroidKeyStore实例
            val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
                // 加载KeyStore
                load(null)
            }
            // 检查KeyStore是否已经包含指定别名的密钥，如果没有，则创建新密钥
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                // 获取KeyGenerator实例，指定使用AES算法和AndroidKeyStore
                val keyGenerator =
                    KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS, // 别名，用于在KeyStore中唯一标识密钥
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT // 指定密钥的用途，这里是用于加密和解密
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM) // 设置块模式为GCM
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE) // 设置不使用填充（因GCM模式本身是不需要填充的）
                        .build() // 构建KeyGenParameterSpec对象
                )
                // 生成密钥
                keyGenerator.generateKey()
            }
        } catch (e: Exception) {
            // 异常处理，打印异常堆栈信息
            e.printStackTrace()
        }
    }



}