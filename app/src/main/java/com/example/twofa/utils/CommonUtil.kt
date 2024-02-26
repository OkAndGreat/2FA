package com.example.twofa.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.LocalWindowInsets

@Composable
fun StatusBarHeight(): Dp {
    // 获取WindowInsets
    val insets = LocalWindowInsets.current.statusBars
    // 将像素转换为DP
    return with(LocalDensity.current) { insets.top.toDp() }
}