package com.example.twofa.ui.secure.widget

import android.text.TextUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twofa.R
import com.example.twofa.utils.Constant

@Composable
fun SecurityCheckItemWidget(
    modifier: Modifier = Modifier,
    painterRes: Int,
    mainText: String,
    extraText: String = "",
    isSelected: Boolean,
    isDisabled: Boolean,
    onSwitched: (() -> Unit)
) {
    var isSwitched by remember(isSelected) {
        mutableStateOf(isSelected)
    }

    val switchColors = SwitchDefaults.colors(
        uncheckedThumbColor = Color.Gray,
        uncheckedTrackColor = Color.LightGray,
        checkedThumbColor = Color.White,
        checkedTrackColor = Color.Red,
        disabledUncheckedTrackColor = Color(255, 253, 253, 253),
        disabledUncheckedThumbColor = Color(225, 225, 225, 255),
        disabledCheckedThumbColor = Color.White,
        disabledCheckedTrackColor = Color.Red
    )

    Row(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = painterRes),
            contentDescription = "",
            tint = Constant.COMMON_RED_COLOR_DEEP, modifier = Modifier.padding(end = 18.dp)
        )

        Column(
            modifier = Modifier
                .width(300.dp)
                .padding(end = 32.dp)
        ) {
            Text(text = mainText, fontSize = 24.sp)
            if (TextUtils.isEmpty(extraText).not()) {
                Text(text = extraText, color = Color.Gray, modifier = Modifier.padding(top = 6.dp))
            }
        }

        Switch(
            checked = isSwitched,
            onCheckedChange = { isSwitched = it },
            colors = switchColors,
            modifier = Modifier.padding(end = 22.dp),
            enabled = isDisabled
        )

    }

}

@Composable
@Preview
fun SecurityCheckItemWidgetPreview() {
    SecurityCheckItemWidget(
        painterRes = R.drawable.ic_pincode,
        mainText = "PIN code",
        extraText = "",
        isSelected = true,
        isDisabled = false,
        onSwitched = {})
}