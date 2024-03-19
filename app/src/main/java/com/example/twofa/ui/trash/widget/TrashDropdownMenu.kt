package com.example.twofa.ui.trash.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.twofa.db.Token
import com.example.twofa.db.emptyToken
import com.example.twofa.ui.token.widget.TextWithCircleBorder
import com.example.twofa.utils.Constant
import com.example.twofa.utils.clickableWithoutRipple

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrashDropdownMenu(
    modifier: Modifier = Modifier,
    onItemClicked: ((token: Token) -> Unit),
    onItemLongClicked: ((secret: String) -> Unit),
    onItemSelected: ((Int) -> Unit),
    tokenMixed: Token,
) {
    ConstraintLayout(
        modifier
            .combinedClickable(onClick = {
                onItemClicked.invoke(tokenMixed)
            }, onLongClick = {
                onItemLongClicked.invoke("")
            })
            .fillMaxWidth()
    ) {
        val (iconRef, infoRef, menuRef) = createRefs()

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
            .padding(start = 15.dp, bottom = 10.dp)) {
            Text(text = tokenMixed.platformName, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(
                text = tokenMixed.userName,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 1.dp)
            )
        }

        ThreeDotMenu(modifier = Modifier
            .constrainAs(menuRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
            .padding(end = 18.dp)) {
            onItemSelected.invoke(it)
        }

        val (dividerRef) = createRefs()
        Divider(
            modifier
                .constrainAs(dividerRef) {
                    start.linkTo(parent.start)
                    top.linkTo(infoRef.bottom)
                    end.linkTo(parent.end)
                }
                .padding(top = 10.dp)
        )
    }
}

@Composable
fun ThreeDotMenu(modifier: Modifier = Modifier, onItemSelected: ((Int) -> Unit)) {
    var expanded by remember { mutableStateOf(false) }


    Box(modifier = modifier) {
        ThreeDot(dotSize = 3.dp, modifier = Modifier.clickableWithoutRipple {
            expanded = true
        })

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                onItemSelected.invoke(0)
                expanded = false
            }) {
                Text(text = "恢复")
            }
            DropdownMenuItem(onClick = {
                onItemSelected.invoke(1)
                expanded = false
            }) {
                Text(text = "从垃圾箱中删除", color = Color.Red)
            }
        }
    }
}


@Composable
fun ThreeDot(modifier: Modifier = Modifier, dotSize: Dp) {
    Column(modifier) {
        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .size(dotSize)
                .background(color = Color.Gray, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .size(dotSize)
                .background(color = Color.Gray, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .size(dotSize)
                .background(color = Color.Gray, shape = CircleShape)
        )
    }
}

@Composable
@Preview
fun TrashDropdownMenuPreview() {
    TrashDropdownMenu(
        onItemClicked = {},
        onItemLongClicked = {},
        onItemSelected = { it: Int -> },
        tokenMixed = emptyToken
    )
}