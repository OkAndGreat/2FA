package com.example.twofa.utils

import android.net.Uri
import java.net.URI

data class TOTPAuthData(
    val platformName: String,
    val userName: String,
    val secretKey: String
)

object TOTPParseUtil {

    fun parseTOTPAuthUrl(totpAuthUrl: String): TOTPAuthData? {
        try {
            val uri = URI(totpAuthUrl)

            // 确保协议是otpauth
            if (uri.scheme != "otpauth" || uri.host != "totp") {
                return null
            }

            // 从路径中移除前导的"/"，然后分割平台名称和用户名
            val path = uri.path.substring(1)
            val pathSegments = path.split(":")
            if (pathSegments.size != 2) {
                return null
            }

            val platformName = pathSegments[0]
            val userName = pathSegments[1]

            // 解析查询参数以获取密钥
            val queryPairs = uri.query.split("&").associate {
                val idx = it.indexOf("=")
                if (idx != -1) it.substring(0, idx) to it.substring(idx + 1) else it to ""
            }

            val secretKey = queryPairs["secret"] ?: return null

            return TOTPAuthData(
                platformName = platformName,
                userName = userName,
                secretKey = secretKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}

