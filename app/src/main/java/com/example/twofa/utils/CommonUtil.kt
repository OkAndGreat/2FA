package com.example.twofa.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.LocalWindowInsets

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

