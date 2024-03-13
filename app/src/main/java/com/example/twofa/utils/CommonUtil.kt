package com.example.twofa.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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

fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier = composed {
    this.then(
        Modifier.clickable(indication = null, interactionSource = remember {
            MutableInteractionSource()
        }) { onClick.invoke() }
    )
}

