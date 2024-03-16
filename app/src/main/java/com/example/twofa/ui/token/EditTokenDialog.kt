package com.example.twofa.ui.token

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.twofa.R
import com.example.twofa.db.Token
import com.example.twofa.db.emptyToken
import com.example.twofa.utils.Constant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTokenDialog(
    index: Int,
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (token: Token, idx: Int) -> Unit,
    onDeleteClicked: (token: Token) -> Unit,
    token: Token
) {

    val platformFieldValue = remember {
        mutableStateOf(TextFieldValue(token.platformName))
    }
    val usernameFieldValue = remember {
        mutableStateOf(TextFieldValue(token.userName))
    }
    val secretFieldValue = remember {
        mutableStateOf(TextFieldValue(token.secretKey))
    }
    var secretVisibility by remember { mutableStateOf(false) }

    val fullWidthModifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 8.dp)

    Dialog(onDismissRequest = onDismiss) {
        Card(elevation = 8.dp, shape = RoundedCornerShape(12.dp)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(
                    text = "修改面板",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )

                OutlinedTextField(
                    modifier = fullWidthModifier.padding(top = 10.dp),
                    value = platformFieldValue.value,
                    label = { Text(text = "平台名") },
                    onValueChange = {
                        platformFieldValue.value = it
                    },
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Constant.COMMON_RED_COLOR_DEEP,
                        focusedLabelColor = Constant.COMMON_RED_COLOR_DEEP,
                        disabledLabelColor = Constant.COMMON_RED_COLOR_DEEP
                    )
                )
                OutlinedTextField(
                    modifier = fullWidthModifier,
                    value = usernameFieldValue.value,
                    label = { Text(text = "用户名") },
                    onValueChange = {
                        usernameFieldValue.value = it
                    },
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Constant.COMMON_RED_COLOR_DEEP,
                        focusedLabelColor = Constant.COMMON_RED_COLOR_DEEP,
                        disabledLabelColor = Constant.COMMON_RED_COLOR_DEEP
                    )
                )
                OutlinedTextField(
                    modifier = fullWidthModifier,
                    value = secretFieldValue.value,
                    label = { Text(text = "密钥") },
                    onValueChange = {
                        secretFieldValue.value = it
                    },
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Constant.COMMON_RED_COLOR_DEEP,
                        focusedLabelColor = Constant.COMMON_RED_COLOR_DEEP,
                        disabledLabelColor = Constant.COMMON_RED_COLOR_DEEP
                    ),
                    visualTransformation = if (secretVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image: Painter = if (secretVisibility) {
                            painterResource(id = R.drawable.ic_visibility_on)

                        } else {
                            painterResource(id = R.drawable.ic_visibility_off)
                        }

                        val description = if (secretVisibility) "Hide password" else "Show password"

                        IconButton(onClick = { secretVisibility = !secretVisibility }) {
                            Icon(painter = image, description)
                        }
                    }
                )

                Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(percent = 30),
                    onClick = {
                        onDeleteClicked.invoke(token)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Constant.COMMON_RED_COLOR_DEEP,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp)
                ) {
                    Text(text = "删除")
                }

                Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {

                    OutlinedButton(
                        shape = RoundedCornerShape(percent = 30),
                        onClick = onNegativeClick,
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Constant.COMMON_RED_COLOR_DEEP,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(start = 24.dp, end = 15.dp)
                            .weight(1f)
                    ) {
                        Text(text = "我再想想")
                    }

                    OutlinedButton(
                        shape = RoundedCornerShape(percent = 30),
                        onClick = {
                            onPositiveClick.invoke(
                                Token(
                                    id = 0,
                                    platformName = platformFieldValue.value.text,
                                    userName = usernameFieldValue.value.text,
                                    secretKey = secretFieldValue.value.text
                                ),
                                index
                            )
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Constant.COMMON_RED_COLOR_DEEP,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(end = 24.dp, start = 15.dp)
                            .weight(1f)
                    ) {
                        Text(text = "确定修改")
                    }
                }

            }
        }
    }

}

@Composable
@Preview
fun EditTokenDialogPreview() {
    EditTokenDialog(
        index = 0,
        onDismiss = { /*TODO*/ },
        onNegativeClick = { /*TODO*/ },
        onPositiveClick = { token, idx -> },
        onDeleteClicked = { /*TODO*/ },
        token = emptyToken
    )
}