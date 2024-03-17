package com.example.twofa.ui.secure

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twofa.R
import com.example.twofa.ui.secure.widget.ConfirmToggleDialog
import com.example.twofa.ui.secure.widget.SecurityCheckItemWidget
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

    val screenshotSelectState by
    secureViewModel.screenshotSelectState
    val showConfirmDialog by secureViewModel.showConfirmToggleDialog

    var pincodeSelectState by remember {
        mutableStateOf(false)
    }
    var biometricsSelectState by remember {
        mutableStateOf(false)
    }

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
            isSelected = false,
            isEnabled = true
        ) {
        }
        Divider()
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_biometrics,
            mainText = "指纹锁",
            isSelected = false,
            isEnabled = false
        ) {

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
