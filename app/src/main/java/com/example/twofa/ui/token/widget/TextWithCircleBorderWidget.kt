package com.example.twofa.ui.token.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twofa.utils.Constant

@Composable
fun TextWithCircleBorder(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape) // 应用圆形裁剪
            .border(2.dp, Color.LightGray, CircleShape) // 应用圆形边框
            .padding(12.dp) // 文字与边框的间距
    ) {
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 10.sp
        )
    }
}

@Composable
@Preview
fun TextWithCircleBorderPreview() {
    TextWithCircleBorder(text = "坚果")
}
