package com.example.twofa.ui.ioport

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.twofa.R
import com.example.twofa.ui.setting.SettingRow
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.IOportViewModel
import widget.NavItem
import widget.NavigationHeader

@Composable
fun IOportScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val iOportViewModel = IOportViewModel.get(context)
    val globalViewModel = GlobalViewModel.get(context = context)
    val navController = globalViewModel?.navController

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavigationHeader(info = "导入与导出", modifier = Modifier.padding(start = 6.dp)) {
            navController?.navigate(NavItem.SettingNavItem.route)
        }

        SettingRow(
            modifier = Modifier,
            iconId = R.drawable.ic_import,
            desc = "导入"
        ) {

        }
        SettingRow(
            modifier = Modifier,
            iconId = R.drawable.ic_outport,
            desc = "导出"
        ) {

        }
    }
}