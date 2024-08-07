package com.example.twofa.ui.trash

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.twofa.R
import com.example.twofa.ui.trash.widget.TrashDropdownMenu
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.TokenViewModel
import com.example.twofa.viewmodel.TrashViewModel
import widget.NavItem
import widget.NavigationHeader

@Composable
fun TrashScreen() {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val trashViewModel = remember {
        TrashViewModel.get(context)!!
    }
    val globalViewModel = GlobalViewModel.get(context = context)
    val navController = globalViewModel?.navController
    val tokenViewModel = TokenViewModel.get(context = context)
    val tokenList by trashViewModel.tokenList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        NavigationHeader(info = "回收站", modifier = Modifier.padding(start = 6.dp)) {
            navController?.navigate(NavItem.SettingNavItem.route)
        }

        if (tokenList.isNotEmpty()) {
            LazyColumn(
                state = listState
            ) {
                itemsIndexed(tokenList) { idx, item ->
                    TrashDropdownMenu(
                        onItemClicked = {},
                        onItemLongClicked = {},
                        onItemSelected = {
                            if (it == 0) {
                                tokenViewModel?.addToken(item)
                                trashViewModel.deleteToken(item)
                                Toast.makeText(context, "恢复成功！", Toast.LENGTH_SHORT).show()
                            } else if (it == 1) {
                                trashViewModel.deleteToken(item)
                                Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show()
                            }
                        },
                        tokenMixed = item
                    )
                }
            }
        } else {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.no_more_msg))
            //转为状态
            val progress by animateLottieCompositionAsState(
                composition,
                iterations = LottieConstants.IterateForever
            )
            //放入显示
            LottieAnimation(composition = composition, progress = progress)
            Text(text = "没有任何消息~")

        }

    }


}