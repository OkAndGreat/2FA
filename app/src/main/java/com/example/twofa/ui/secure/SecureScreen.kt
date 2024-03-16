package com.example.twofa.ui.secure

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twofa.R
import com.example.twofa.ui.secure.widget.SecurityCheckItemWidget
import com.example.twofa.viewmodel.GlobalViewModel
import widget.NavItem
import widget.NavigationHeader

@Composable
fun SecureScreen() {
    val globalViewModel: GlobalViewModel = viewModel()
    val navController = globalViewModel.navController

    Column {
        NavigationHeader(info = "安全设置") {
            navController?.navigate(NavItem.SettingNavItem.route)
        }
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_pincode,
            mainText = "PIN code",
            isSelected = false,
            isDisabled = false
        ) {

        }
        Divider()
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_biometrics,
            mainText = "指纹锁",
            isSelected = false,
            isDisabled = false
        ) {

        }
        Divider()
        SecurityCheckItemWidget(
            painterRes = R.drawable.ic_screenshot,
            mainText = "允许截屏",
            extraText = "开启后将允许在应用内截图，该选项默认为关闭",
            isSelected = false,
            isDisabled = false
        ) {

        }
    }


}

@Composable
@Preview
fun SecureScreenPreview() {
    SecureScreen()
}
