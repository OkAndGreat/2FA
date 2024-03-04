package com.example.twofa.ui.token.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddButton(modifier: Modifier = Modifier, onItemClicked: (() -> Unit)) {
    Box(
        modifier
            .clickable {
                onItemClicked.invoke()
            }
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Red)
    ) {
        Text(
            text = "＋",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White,
            fontWeight = FontWeight.Black,
        )
    }
}

@Preview
@Composable
fun AddButtonPreview() {
    AddButton(Modifier.size(40.dp), onItemClicked = {})
}