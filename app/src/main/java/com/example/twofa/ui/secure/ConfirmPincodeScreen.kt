package com.example.twofa.ui.secure

import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.biometric.BiometricManager
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
import com.example.twofa.utils.BiometricUtil
import com.example.twofa.utils.Constant
import com.example.twofa.utils.clickableWithoutRipple
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.SecurityViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import widget.NavigationHeader

@Composable
fun ConfirmPincodeScreen() {
    val globalViewModel: GlobalViewModel =
        viewModel(LocalView.current.findViewTreeViewModelStoreOwner()!!)
    val secureViewModel = SecurityViewModel.get(LocalContext.current)!!
    val navController = globalViewModel.navController
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)
    val coroutineScope = rememberCoroutineScope()
    var confirmPinCode by remember {
        mutableStateOf("")
    }

    var isConfirmSuccess = false

    val verifySuccess = {
        isConfirmSuccess = true
        coroutineScope.launch {
            Toast.makeText(context, "验证成功！", Toast.LENGTH_SHORT).show()
            delay(1000)
            val previousBackStackEntry = navController?.previousBackStackEntry
            previousBackStackEntry?.savedStateHandle?.set(
                Constant.KEY_CONFIRM_PIN,
                isConfirmSuccess
            )
            navController?.popBackStack()
        }
    }

    BackHandler(enabled = true) {
        val previousBackStackEntry = navController?.previousBackStackEntry
        previousBackStackEntry?.savedStateHandle?.set(
            Constant.KEY_CONFIRM_PIN,
            isConfirmSuccess
        )
        navController?.popBackStack()
    }

    if ((biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                == BiometricManager.BIOMETRIC_SUCCESS) && secureViewModel.biometricsSelectState.value
    ) {
        BiometricUtil.verifyBiometric(context = context, onVerifySuccess = {
            verifySuccess.invoke()
        }, onVerifyFailed = {
            isConfirmSuccess = false
            Toast.makeText(context, "验证失败！", Toast.LENGTH_SHORT).show()
        })
    }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationHeader(info = "验证 PIN 码", modifier = Modifier.padding(start = 6.dp)) {
            val previousBackStackEntry = navController?.previousBackStackEntry
            previousBackStackEntry?.savedStateHandle?.set(
                Constant.KEY_CONFIRM_PIN,
                isConfirmSuccess
            )
            navController?.popBackStack()
        }


        Text(
            text = "请输入验证Pin码",
            modifier = Modifier.padding(top = 100.dp),
            fontSize = 18.sp
        )


        Box {
            FourDotWidget(
                fillCount = confirmPinCode.length,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .align(Alignment.Center)
            )
            Icon(painter = painterResource(id = R.drawable.ic_clear_soon),
                contentDescription = "",
                tint = if (confirmPinCode.isEmpty()) Color.LightGray else Color.Black,
                modifier = Modifier
                    .padding(top = 28.dp, end = 66.dp)
                    .clickableWithoutRipple {
                        confirmPinCode = ""
                    }
                    .size(18.dp)
                    .align(Alignment.CenterEnd))
        }

        Divider(modifier = Modifier.padding(top = 24.dp, start = 36.dp, end = 36.dp))

        DigitPanelWidget(digitSize = 24.sp, modifier = Modifier.padding(top = 24.dp)) {
            confirmPinCode += it.toString()
            if (confirmPinCode.length == 4) {
                if (TextUtils.equals(confirmPinCode, secureViewModel.pincode.value)) {
                    verifySuccess.invoke()
                }
            } else {
                isConfirmSuccess = false
                Toast.makeText(context, "验证失败！", Toast.LENGTH_SHORT).show()
                confirmPinCode = ""
            }

        }

    }
}

