package com.example.twofa.ui.apperance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.twofa.R
import com.example.twofa.ui.secure.widget.CommonCheckItemWidget
import com.example.twofa.viewmodel.AppearanceViewModel
import com.example.twofa.viewmodel.GlobalViewModel
import widget.NavItem
import widget.NavigationHeader

@Composable
fun ApperanceScreen() {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val appearanceViewModel = AppearanceViewModel.get(context = context)!!
    val globalViewModel = GlobalViewModel.get(context = context)
    val navController = globalViewModel?.navController

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavigationHeader(info = "行为与外观", modifier = Modifier.padding(start = 6.dp)) {
            navController?.navigate(NavItem.SettingNavItem.route)
        }

        val showNextTokenSelectState by appearanceViewModel.showNextTokenSelectState
        val hideTokenSelectState by appearanceViewModel.hideTokenSelectState

        CommonCheckItemWidget(
            painterRes = R.drawable.ic_back_5s_48,
            mainText = "显示下一个令牌",
            extraText = "在当前token还有5s过期时显示下一个token",
            isSelected = showNextTokenSelectState
        ) {
            appearanceViewModel.toggleShowNextTokenSelectState()
        }

        Divider(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 15.dp)
        )


        CommonCheckItemWidget(
            painterRes = if (hideTokenSelectState) R.drawable.ic_visibility_off else R.drawable.ic_visibility_on,
            mainText = "隐藏Token",
            extraText = "Token默认展示为 * * * * * * ，点按可以切换展示模式",
            isSelected = hideTokenSelectState
        ) {
            appearanceViewModel.toggleHideTokenSelectState()
        }

        Divider(
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(horizontal = 15.dp)
        )
    }
}