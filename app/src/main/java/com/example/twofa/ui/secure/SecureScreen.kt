package com.example.twofa.ui.secure

import android.app.Activity
import android.view.WindowManager
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.twofa.R
import com.example.twofa.ui.secure.widget.ConfirmToggleDialog
import com.example.twofa.ui.secure.widget.SecurityCheckItemWidget
import com.example.twofa.utils.BiometricUtil
import com.example.twofa.utils.Constant
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.SecurityViewModel
import com.tencent.mmkv.MMKV
import widget.NavItem
import widget.NavigationHeader

@Composable
fun SecureScreen() {
    val globalViewModel: GlobalViewModel =
        viewModel(LocalView.current.findViewTreeViewModelStoreOwner()!!)
    val secureViewModel = SecurityViewModel.get(LocalContext.current)!!
    val navController = globalViewModel.navController
    val kv = MMKV.defaultMMKV()
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)
    // 使用当前BackStackEntryAsState监听返回数据
    val currentBackStackEntry = navController?.currentBackStackEntryAsState()
    val savedStateHandle = currentBackStackEntry?.value?.savedStateHandle
    val isConfirmSuccess = savedStateHandle?.get<Boolean>(Constant.KEY_CONFIRM_PIN).apply {
        savedStateHandle?.set(Constant.KEY_CONFIRM_PIN, false)
    }

    val screenshotSelectState by
    secureViewModel.screenshotSelectState
    val pincodeSelectState by secureViewModel.pincodeSelectState
    val biometricsSelectState by secureViewModel.biometricsSelectState
    val showConfirmDialog by secureViewModel.showConfirmToggleDialog

    val toggleScreenshotSelectState = {
        secureViewModel.toggleScreenshotSelectState()
        if (screenshotSelectState) {
            (context as? Activity)?.apply {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        } else {
            (context as? Activity)?.apply {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }

    if (isConfirmSuccess == true) {
        secureViewModel.togglePincodeSelectState()
    }

    if (showConfirmDialog) {
        ConfirmToggleDialog(onDismiss = {
            secureViewModel.showConfirmToggleDialog.value = false
        }) {
            secureViewModel.showConfirmToggleDialog.value = false
            toggleScreenshotSelectState.invoke()
        }
    }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        NavigationHeader(info = "安全设置", modifier = Modifier.padding(start = 6.dp)) {
            navController?.navigate(NavItem.SettingNavItem.route)
        }

        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_pincode,
            mainText = "PIN code",
            isSelected = pincodeSelectState,
            isEnabled = true
        ) {
            if (pincodeSelectState) {
                navController?.navigate(NavItem.ConfirmPinNavItem.route)

            } else {
                navController?.navigate(NavItem.SetPinNavItem.route)
            }
        }
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_passwordchange,
            mainText = "更改 PIN code",
            useSwitch = false,
            onItemClicked = {
                navController?.navigate(NavItem.ChangePinNavItem.route)
            }
        )

        Divider()
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_biometrics,
            mainText = "指纹锁",
            isSelected = false,
            isEnabled = pincodeSelectState,
            extraText = "指纹锁是相比较Pin码更严格的安全模式，因此需要在Pin码启用时才可以使用 " +
                    "\n当开启指纹锁验证时，允许使用指纹锁验证Pin码验证的场景"
        ) {
            if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                == BiometricManager.BIOMETRIC_SUCCESS
            ) {
                BiometricUtil.verifyBiometric(context, onVerifySuccess = {
                    secureViewModel.toggleBiometricsSelectState()
                }, onVerifyFailed = {

                })
            } else {
                Toast.makeText(context, "当前设备不支持指纹验证", Toast.LENGTH_SHORT).show()
            }
        }
        Divider()
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_screenshot,
            mainText = "允许截屏",
            extraText = "开启后将允许在应用内截图，该选项默认为关闭",
            isSelected = screenshotSelectState,
            isEnabled = true
        ) {
            if (screenshotSelectState.not()) {
                secureViewModel.showConfirmToggleDialog.value = true
            } else {
                toggleScreenshotSelectState.invoke()
            }

        }
    }

}

@Composable
@Preview
fun SecureScreenPreview() {
    SecureScreen()
}
