package com.example.twofa.ui.token.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import widget.AnimatedCircleProgress

@Composable
fun TokenFeedItem(
    modifier: Modifier,
    onItemClicked: (() -> Unit),
    platformName: String,
    userName: String,
    token: String,
    progress: Int,
    maxProgress: Int
) {
    ConstraintLayout(
        modifier
            .clickable {
                onItemClicked.invoke()
            }
            .padding(vertical = 10.dp)
            .fillMaxWidth()) {
        val (iconRef, infoRef, timerRef) = createRefs()

        TextWithCircleBorder(text = "Github", modifier = Modifier
            .constrainAs(iconRef) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .padding(start = 18.dp))

        Column(modifier = Modifier
            .constrainAs(infoRef) {
                start.linkTo(iconRef.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .padding(start = 15.dp)) {
            Text(text = platformName, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(
                text = userName,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 1.dp)
            )
            Text(
                text = token.substring(0, 3) + " " + token.substring(3, 6),
                modifier = Modifier.padding(top = 3.dp),
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraLight
            )
        }

        AnimatedCircleProgress(
            modifier = Modifier
                .constrainAs(timerRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .padding(end = 18.dp)
                .size(40.dp),
            curProgress = progress,
            maxProgress = maxProgress,
            circleColor = Color.Black
        )
    }
}

@Composable
@Preview
fun TokenFeedItemPreview() {
    TokenFeedItem(
        modifier = Modifier,
        onItemClicked = { /*TODO*/ },
        platformName = "Github",
        userName = "OkAndGreat",
        token = "372848",
        progress = 26,
        maxProgress = 30
    )
}