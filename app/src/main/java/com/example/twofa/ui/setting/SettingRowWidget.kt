package com.example.twofa.ui.setting

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.twofa.R

@Composable
fun SettingRow(
    modifier: Modifier,
    @DrawableRes iconId: Int,
    desc: String,
    onItemClicked: (() -> Unit)
) {
    ConstraintLayout(
        modifier = modifier
            .clickable {
                onItemClicked.invoke()
            }
            .padding(horizontal = 20.dp)
    ) {
        val (image, text, divider) = createRefs()

        Icon(
            modifier = Modifier.constrainAs(
                image
            ) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            painter = painterResource(id = iconId),
            contentDescription = "",
            tint = Color.Red
        )

        Text(modifier = Modifier
            .constrainAs(text) {
                start.linkTo(image.end)
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
            }
            .padding(start = 25.dp, bottom = 4.dp),
            text = desc,
            textAlign = TextAlign.Center,
            fontSize = 17.sp, color = Color.Black)

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(divider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(image.bottom)
                }

        )
    }
}

@Composable
@Preview
fun SettingRowPreview() {
    SettingRow(modifier = Modifier, iconId = R.drawable.ic_security_48, "安全", {})
}

