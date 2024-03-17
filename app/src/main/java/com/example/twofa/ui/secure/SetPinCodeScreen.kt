package com.example.twofa.ui.secure

import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twofa.R
import com.example.twofa.ui.secure.widget.DigitPanelWidget
import com.example.twofa.utils.clickableWithoutRipple
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.SecurityViewModel
import com.tencent.mmkv.MMKV
import widget.NavItem
import widget.NavigationHeader

@Composable
fun SetPinCodeScreen() {
    val globalViewModel: GlobalViewModel =
        viewModel(LocalView.current.findViewTreeViewModelStoreOwner()!!)
    val secureViewModel = SecurityViewModel.get(LocalContext.current)!!
    val navController = globalViewModel.navController
    val kv = MMKV.defaultMMKV()
    val context = LocalContext.current


    val pincode by secureViewModel.pincode
    var pinState by remember {
        mutableStateOf(PinState(PINStage.STAGE_FIRST, "", ""))
    }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationHeader(info = "创建 PIN", modifier = Modifier.padding(start = 6.dp)) {
            navController?.navigate(NavItem.SettingNavItem.route)
        }

        Text(
            text = if (pinState.stage == PINStage.STAGE_FIRST) "请输入4位数的PIN码" else "请再次输入",
            modifier = Modifier.padding(top = 100.dp),
            fontSize = 18.sp
        )

        Box {
            FourDotWidget(
                fillCount = if (pinState.stage == PINStage.STAGE_FIRST) pinState.firstPincode.length
                else pinState.confirmPincode.length,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .align(Alignment.Center)
            )
            Icon(painter = painterResource(id = R.drawable.ic_clear_soon),
                contentDescription = "",
                tint = if (pincode.isEmpty()) Color.LightGray else Color.Black,
                modifier = Modifier
                    .padding(top = 28.dp, end = 66.dp)
                    .clickableWithoutRipple {
                        secureViewModel.changePin("")
                    }
                    .size(18.dp)
                    .align(Alignment.CenterEnd))
        }

        Divider(modifier = Modifier.padding(top = 24.dp, start = 36.dp, end = 36.dp))

        DigitPanelWidget(digitSize = 24.sp, modifier = Modifier.padding(top = 24.dp)) {
            if (pinState.stage == PINStage.STAGE_FIRST) {
                pinState.firstPincode += it.toString()
                if (pinState.firstPincode.length == 4) {
                    pinState = pinState.copy(stage = PINStage.STAGE_CONFIRM)
                }
            } else if (pinState.stage == PINStage.STAGE_CONFIRM) {
                pinState.confirmPincode += it.toString()
                if (pinState.confirmPincode.length == 4) {
                    if (TextUtils.equals(pinState.firstPincode, pinState.confirmPincode)) {
                        secureViewModel.changePin(pinState.firstPincode)
                        navController?.popBackStack()
                    } else {
                        Toast.makeText(context, "俩次密码不相同，请再次输入", Toast.LENGTH_SHORT)
                            .show()
                        pinState = PinState(PINStage.STAGE_FIRST, "", "")
                    }
                }
            }
        }
    }
}


@Composable
fun FourDotWidget(modifier: Modifier = Modifier, fillCount: Int) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(12.dp) // 你可以根据需要调整大小
                .border(
                    width = if (fillCount >= 1) 0.dp else 1.dp,
                    color = Color.LightGray,
                    shape = androidx.compose.foundation.shape.CircleShape
                ) // 边框宽度和颜色
                .background(
                    color = if (fillCount >= 1) Color.Gray else Color.Transparent,
                    shape = CircleShape
                ) // 确保中心是透明的
        )
        Box(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(12.dp) // 你可以根据需要调整大小
                .border(
                    width = if (fillCount >= 2) 0.dp else 1.dp,
                    color = Color.LightGray,
                    shape = androidx.compose.foundation.shape.CircleShape
                ) // 边框宽度和颜色
                .background(
                    color = if (fillCount >= 2) Color.Gray else Color.Transparent,
                    shape = CircleShape
                ) // 确保中心是透明的
        )
        Box(
            modifier = Modifier
                .padding(end = 12.dp)
                .size(12.dp) // 你可以根据需要调整大小
                .border(
                    width = if (fillCount >= 3) 0.dp else 1.dp,
                    color = Color.LightGray,
                    shape = androidx.compose.foundation.shape.CircleShape
                ) // 边框宽度和颜色
                .background(
                    color = if (fillCount >= 3) Color.Gray else Color.Transparent,
                    shape = CircleShape
                ) // 确保中心是透明的
        )
        Box(
            modifier = Modifier
                .size(12.dp) // 你可以根据需要调整大小
                .border(
                    width = if (fillCount >= 4) 0.dp else 1.dp,
                    color = Color.LightGray,
                    shape = androidx.compose.foundation.shape.CircleShape
                ) // 边框宽度和颜色
                .background(
                    color = if (fillCount >= 4) Color.Gray else Color.Transparent,
                    shape = CircleShape
                ) // 确保中心是透明的

        )


    }
}

data class PinState(var stage: PINStage, var firstPincode: String, var confirmPincode: String)

enum class PINStage {
    STAGE_FIRST, STAGE_CONFIRM
}


@Composable
@Preview
fun SetPinCodeScreenPreview() {
    SetPinCodeScreen()
}
