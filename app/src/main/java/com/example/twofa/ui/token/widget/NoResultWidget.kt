package com.example.twofa.ui.token.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.twofa.utils.Constant

@Composable
fun NoResultWidget(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = "",
            tint = Constant.COMMON_RED_COLOR_DEEP,
            modifier = Modifier.size(66.dp)
        )
        Text(text = "抱歉，没有结果！", color = Constant.COMMON_RED_COLOR_DEEP)
    }
}

@Preview
@Composable
fun NoResultWidgetPreview() {
    NoResultWidget()
}