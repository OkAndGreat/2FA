package com.example.twofa.ui.secure.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PasswordAnimation(showConfirm: Boolean, modifier: Modifier = Modifier) {
    // 初始状态
    var showPasswordPrompt by remember(showConfirm) { mutableStateOf(!showConfirm) }

    // 密度，用于将dp单位转换为像素
    val density = LocalDensity.current

    // 动画：文本从右到左移动
    val textOffset by animateFloatAsState(
        // 如果显示密码提示，则偏移量为0，否则为负值，将文本移出屏幕
        targetValue = if (showPasswordPrompt) 0f else with(density) { -300.dp.toPx() },
        animationSpec = tween(durationMillis = 1000) // 动画时长
    )

    // 使用Box作为布局容器
    Box(
        modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = "请输入4位数的PIN码",
            modifier = Modifier
                .graphicsLayer { translationX = textOffset }
                .align(Alignment.Center),
            fontSize = 18.sp
        )

        Text(
            text = "请再次输入",
            modifier = Modifier
                .graphicsLayer {
                    translationX = textOffset + with(density) { 300.dp.toPx() }
                }
                .align(Alignment.Center),
            fontSize = 18.sp
        )

    }
}

