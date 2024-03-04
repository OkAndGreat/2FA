package com.example.twofa.ui.token

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.example.twofa.ParseQRCodeEvent
import com.example.twofa.R
import com.example.twofa.ui.token.widget.AddButton
import com.example.twofa.utils.Constant.REQUEST_CODE_SCAN
import com.example.twofa.utils.LogUtil
import com.king.camera.scan.util.LogUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Composable
fun TokenScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        //上部区域，包括搜索框和添加按钮
        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            AddButton(
                Modifier
                    .padding(top = 8.dp)
                    .size(35.dp)
                    .align(Alignment.TopEnd)
            ) {
                val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(
                    context,
                    R.anim.`in`,
                    R.anim.out
                )
                val intent = Intent(context, QRCodeScanActivity::class.java)
                ActivityCompat.startActivityForResult(
                    (context as Activity),
                    intent,
                    REQUEST_CODE_SCAN,
                    optionsCompat.toBundle()
                )
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onParseQRCodeEvent(event: ParseQRCodeEvent) {
        LogUtil.d(event.msg)
    }

}


@Composable
@Preview
fun TokenScreenPreview() {
    TokenScreen()
}