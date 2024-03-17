package com.example.twofa.ui.secure.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.twofa.utils.Constant

@Composable
fun ConfirmToggleDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(elevation = 8.dp, shape = RoundedCornerShape(12.dp), modifier = modifier) {
            Column(
                modifier = Modifier
                    .width(400.dp)
                    .wrapContentHeight()
                    .background(Color.White)
                    .padding(8.dp)
            ) {

                Text(
                    text = "确认允许应用内截屏？",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )

                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        "请再次确认同意应用内截屏。同意该操作后，你将允许可以在应用内截屏，" +
                                "然而，其他外部应用也将获得权限可以获取屏幕截图\n为了安全考虑，请您操作完后关闭此选项",
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                DialogButtons(onDismiss, onConfirm)
            }
        }
    }
}

@Composable
private fun DialogButtons(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = onDismiss,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.textButtonColors(contentColor = Constant.COMMON_RED_COLOR_DEEP)
        ) {
            Text(text = "我再想想")
        }
        TextButton(
            onClick = onConfirm,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.textButtonColors(contentColor = Constant.COMMON_RED_COLOR_DEEP)
        ) {
            Text(text = "确认")
        }
    }
}

@Composable
@Preview
fun ConfirmToggleDialogPreview() {
    ConfirmToggleDialog(onDismiss = { /*TODO*/ }) {

    }
}