package com.example.twofa.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.example.twofa.db.Token
import com.google.accompanist.insets.LocalWindowInsets
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.IOException

object CommonUtil {

    /**
     * 复制文本到剪贴板。
     *
     * @param context 上下文对象
     * @param text 要复制到剪贴板的文本
     */
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.setPrimaryClip(clip)
    }

    /**
     * 从剪贴板获取文本。
     *
     * @param context 上下文对象
     * @return 返回剪贴板上的文本，如果剪贴板为空，则返回null。
     */
    fun getTextFromClipboard(context: Context): String? {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (!clipboard.hasPrimaryClip()) {
            return null
        }
        val clipData = clipboard.primaryClip
        if (clipData != null && clipData.itemCount > 0) {
            val item = clipData.getItemAt(0)
            return item.text.toString()
        }
        return null
    }

    fun exportTokens(context: Context, tokens: List<Token>) {
        val gson = Gson()
        val tokensJson = gson.toJson(tokens)
        val fileName = "tokens.2fa"
        try {
            val dir = context.getExternalFilesDir(null)
            val file = File(dir, fileName)
            FileWriter(file).use { writer ->
                writer.write(tokensJson)
            }
            Toast.makeText(context, "文件已被成功保存到${dir?.absolutePath}", Toast.LENGTH_SHORT)
                .show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun openFileChooser(context: Context, startFileChooser: ActivityResultLauncher<Intent>) {
        // 创建Intent来打开文件选择器
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*" // 这里可以设置为任意文件类型，或者指定为你的MIME类型

            // 可以使用下面的代码指定默认目录，但请注意这不总是有效，因为不是所有的文件选择器应用都会尊重这个额外的参数
            val directoryUri = Uri.parse("file://" + context.getExternalFilesDir(null).toString())
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, directoryUri)
        }

        // 启动文件选择器
        startFileChooser.launch(intent)
    }

}

@Composable
fun StatusBarHeight(): Dp {
    // 获取WindowInsets
    val insets = LocalWindowInsets.current.statusBars
    // 将像素转换为DP
    return with(LocalDensity.current) { insets.top.toDp() }
}

fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier = composed {
    this.then(
        Modifier.clickable(indication = null, interactionSource = remember {
            MutableInteractionSource()
        }) { onClick.invoke() }
    )
}

