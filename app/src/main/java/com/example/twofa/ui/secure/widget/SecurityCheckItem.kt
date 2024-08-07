package com.example.twofa.ui.secure.widget

import android.text.TextUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.twofa.utils.clickableWithoutRipple

@Composable
fun CommonCheckItemWidget(
    modifier: Modifier = Modifier,
    painterRes: Int,
    mainText: String,
    extraText: String = "",
    isSelected: Boolean = false,
    isEnabled: Boolean = true,
    useSwitch: Boolean = true,
    onItemClicked: (() -> Unit) = {},
    onSwitched: (() -> Unit) = {}
) {
    var isSwitched by remember(isSelected) {
        mutableStateOf(isSelected)
    }

    val switchColors = SwitchDefaults.colors(
        uncheckedThumbColor = Color.Gray,
        uncheckedTrackColor = Color.LightGray,
        checkedThumbColor = Color.White,
        checkedTrackColor = Color.Red,
        disabledUncheckedTrackColor = Color(225, 225, 225, 255),
        disabledUncheckedThumbColor = Color(225, 225, 225, 255),
        disabledCheckedThumbColor = Color.White,
        disabledCheckedTrackColor = Color.Red
    )

    Row(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickableWithoutRipple {
                onItemClicked.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = painterRes),
            contentDescription = "",
            tint = Constant.COMMON_RED_COLOR_DEEP, modifier = Modifier.padding(end = 18.dp).size(20.dp)
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

        if (useSwitch) {
            Switch(
                checked = isSwitched,
                onCheckedChange = {
                    onSwitched.invoke()
                },
                colors = switchColors,
                modifier = Modifier.padding(end = 22.dp),
                enabled = isEnabled
            )
        }
    }

}

@Composable
@Preview
fun CommonCheckItemWidgetPreview() {
    CommonCheckItemWidget(
        painterRes = R.drawable.ic_pincode,
        mainText = "PIN code",
        extraText = "",
        isSelected = true,
        isEnabled = false,
        onSwitched = {})
}