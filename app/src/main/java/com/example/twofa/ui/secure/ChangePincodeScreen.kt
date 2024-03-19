package com.example.twofa.ui.secure

import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twofa.R
import com.example.twofa.ui.secure.widget.DigitPanelWidget
import com.example.twofa.ui.secure.widget.PasswordAnimation
import com.example.twofa.utils.clickableWithoutRipple
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.SecurityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import widget.NavigationHeader

@Composable
fun ChangePincodeScreen() {

    val globalViewModel: GlobalViewModel =
        viewModel(LocalView.current.findViewTreeViewModelStoreOwner()!!)
    val secureViewModel = SecurityViewModel.get(LocalContext.current)!!
    val navController = globalViewModel.navController
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var pinState by remember {
        mutableStateOf(ChangePinState(ChangePINStage.STATE_VERIFY, "", "", ""))
    }

    BackHandler(enabled = true) {
        navController?.popBackStack()
    }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationHeader(info = "更改 PIN", modifier = Modifier.padding(start = 6.dp)) {
            navController?.popBackStack()
        }

        val text = when (pinState.stage) {
            ChangePINStage.STATE_VERIFY -> "请输入原PIN码"
            ChangePINStage.STAGE_FIRST -> "请输入4位数的PIN码"
            ChangePINStage.STAGE_CONFIRM -> "请再次输入"
        }
        Text(text = text, modifier = Modifier.padding(top = 100.dp), fontSize = 18.sp)


        Box {
            val fillCount = when (pinState.stage) {
                ChangePINStage.STATE_VERIFY -> pinState.verifyPincode.length
                ChangePINStage.STAGE_FIRST -> pinState.firstPincode.length
                ChangePINStage.STAGE_CONFIRM -> pinState.confirmPincode.length
            }
            FourDotWidget(
                fillCount = fillCount,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .align(Alignment.Center)
            )
            Icon(painter = painterResource(id = R.drawable.ic_clear_soon),
                contentDescription = "",
                tint = if (fillCount == 0) Color.LightGray else Color.Black,
                modifier = Modifier
                    .padding(top = 28.dp, end = 66.dp)
                    .clickableWithoutRipple {
                        pinState = when (pinState.stage) {
                            ChangePINStage.STATE_VERIFY -> pinState.copy(verifyPincode = "")

                            ChangePINStage.STAGE_FIRST -> pinState.copy(firstPincode = "")

                            ChangePINStage.STAGE_CONFIRM -> pinState.copy(verifyPincode = "")
                        }
                    }
                    .size(18.dp)
                    .align(Alignment.CenterEnd))
        }

        Divider(modifier = Modifier.padding(top = 24.dp, start = 36.dp, end = 36.dp))

        DigitPanelWidget(digitSize = 24.sp, modifier = Modifier.padding(top = 24.dp)) {
            when (pinState.stage) {
                ChangePINStage.STATE_VERIFY -> {
                    pinState = pinState.copy(verifyPincode = pinState.verifyPincode + it.toString())
                    if (pinState.verifyPincode.length == 4) {
                        pinState = if (TextUtils.equals(
                                pinState.verifyPincode,
                                secureViewModel.pincode.value
                            )
                        ) {
                            pinState.copy(stage = ChangePINStage.STAGE_FIRST)
                        } else {
                            Toast.makeText(context, "PIN错误", Toast.LENGTH_SHORT).show()
                            pinState.copy(verifyPincode = "")
                        }

                    }
                }

                ChangePINStage.STAGE_FIRST -> {
                    pinState = pinState.copy(firstPincode = pinState.firstPincode + it.toString())
                    if (pinState.firstPincode.length == 4) {
                        pinState = pinState.copy(stage = ChangePINStage.STAGE_CONFIRM)
                    }
                }

                ChangePINStage.STAGE_CONFIRM -> {
                    pinState =
                        pinState.copy(confirmPincode = pinState.confirmPincode + it.toString())
                    if (pinState.confirmPincode.length == 4) {
                        if (TextUtils.equals(pinState.firstPincode, pinState.confirmPincode)) {
                            secureViewModel.changePin(pinState.firstPincode)
                            secureViewModel.togglePincodeSelectState()
                            coroutineScope.launch {
                                Toast.makeText(context, "设置成功！", Toast.LENGTH_SHORT)
                                    .show()
                                delay(1500)
                                navController?.popBackStack()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "俩次密码输入不相同，请再次输入",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            pinState =
                                ChangePinState(ChangePINStage.STAGE_FIRST, "", "", "")
                        }
                    }
                }
            }
        }
    }
}

data class ChangePinState(
    var stage: ChangePINStage,
    var verifyPincode: String,
    var firstPincode: String,
    var confirmPincode: String
)

enum class ChangePINStage {
    STATE_VERIFY, STAGE_FIRST, STAGE_CONFIRM
}
