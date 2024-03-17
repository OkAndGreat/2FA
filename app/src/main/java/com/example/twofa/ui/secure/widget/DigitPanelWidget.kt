package com.example.twofa.ui.secure.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twofa.utils.clickableWithoutRipple

@Composable
fun DigitPanelWidget(
    modifier: Modifier = Modifier, digitSize: TextUnit, onDigitClicked: (Int) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "1", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(1)
            })
            Text(text = "2", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(2)
            })
            Text(text = "3", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(3)
            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "4", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(4)
            })
            Text(text = "5", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(5)
            })
            Text(text = "6", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(6)
            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "7", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(7)
            })
            Text(text = "8", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(8)
            })
            Text(text = "9", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
                onDigitClicked.invoke(9)
            })
        }

        Text(text = "0", fontSize = digitSize, modifier = Modifier.clickableWithoutRipple {
            onDigitClicked.invoke(0)
        })
    }
}

@Composable
@Preview
fun DigitPanelWidgetPreview() {
    DigitPanelWidget(digitSize = 24.sp) {

    }
}
