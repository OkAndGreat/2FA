package com.example.twofa.utils

import android.util.Base64
import com.example.twofa.MyApplication.Companion.KEY_ALIAS
import com.tencent.mmkv.MMKV
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * 在Android开发中，SharedPreferences (SP) 是一种轻量级的存储方案，适用于保存少量的数据，如用户的设置。
 * 然而，SharedPreferences本身并不提供数据加密功能，存储的数据以明文形式保存在XML文件中，这可能会带来安全隐患。
 * 要确保SharedPreferences存储的数据安全,可以使用keystore
 * Android Keystore System为加密操作提供了硬件级别的安全性，使得密钥材料不会被直接暴露给应用程序
 */
object EncryptUtil {

    fun saveEncryptedData(data: String, key: String) {
        val mmkv = MMKV.defaultMMKV()
        val encryptedData = encryptText(data)
        mmkv.encode(key, encryptedData)
    }

    fun getDecryptedData(key: String): String? {
        val mmkv = MMKV.defaultMMKV()
        val encryptedData = mmkv.decodeString(key)
        if (encryptedData != null) {
            try {
                return decryptText(encryptedData)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    @Throws(Exception::class)
    private fun encryptText(textToEncrypt: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.getIV()
        val encryption = cipher.doFinal(textToEncrypt.toByteArray(StandardCharsets.UTF_8))
        val encryptedData =
            ByteBuffer.allocate(iv.size + encryption.size).put(iv).put(encryption).array()
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    private fun decryptText(encryptedData: String): String {
        val data: ByteArray = Base64.decode(encryptedData, Base64.DEFAULT)
        val byteBuffer = ByteBuffer.wrap(data)
        val iv = ByteArray(12)
        byteBuffer[iv]
        val cipherText = ByteArray(byteBuffer.remaining())
        byteBuffer[cipherText]
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(128, iv))
        val decryptedText = cipher.doFinal(cipherText)
        return String(decryptedText, StandardCharsets.UTF_8)
    }

    @Throws(Exception::class)
    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
    }
}