package com.example.twofa.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.twofa.R

@Composable
fun SettingScreen() {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Setting",
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Column() {
            SettingRow(modifier = Modifier, iconId = R.drawable.ic_security_48, desc = "安全中心") {

            }
            SettingRow(modifier = Modifier, iconId = R.drawable.ic_theme_48, desc = "行为与外观") {

            }
            SettingRow(modifier = Modifier, iconId = R.drawable.ic_import_outport, desc = "导入与导出") {

            }
            SettingRow(modifier = Modifier, iconId = R.drawable.ic_trash_48, desc = "回收站") {

            }

        }
    }
}