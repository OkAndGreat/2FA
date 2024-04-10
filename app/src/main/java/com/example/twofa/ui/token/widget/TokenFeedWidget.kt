package com.example.twofa.ui.token.widget

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.twofa.db.Token
import com.example.twofa.db.emptyToken
import com.example.twofa.ui.token.EditTokenDialog
import com.example.twofa.utils.CommonUtil
import com.example.twofa.viewmodel.AppearanceViewModel
import com.example.twofa.viewmodel.TokenViewModel
import com.example.twofa.viewmodel.TrashViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun TokenFeedWidget(modifier: Modifier = Modifier, tokenFeedList: List<Token>) {

    var currentProgress by remember {
        mutableStateOf(0)
    }
    var showDialog by remember { mutableStateOf(false) }
    var curSelectedIndex by remember {
        mutableStateOf(0)
    }
    var dialogToken by remember {
        mutableStateOf(emptyToken.copy())
    }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val tokenViewModel = TokenViewModel.get(context)
    val trashViewModel = TrashViewModel.get(context)
    val appearanceViewModel = AppearanceViewModel.get(context)!!

    LaunchedEffect(Unit) {

        val ticketFlow = flow {
            var seconds = 0L
            while (true) {
                emit(seconds)
                delay(1000L)
                seconds++
            }
        }
        ticketFlow.collect {
            val currentTime = System.currentTimeMillis() / 1000
            val timeStep = 30
            var startTime = 0 //暂时为0
            val timeSinceStartTime = currentTime - startTime
            currentProgress = (timeSinceStartTime % timeStep).toInt()
        }
    }
    val showNextTokenSelectState by appearanceViewModel.showNextTokenSelectState
    val hideTokenSelectState = remember {
        mutableStateOf(appearanceViewModel.hideTokenSelectState.value)
    }


    if (showDialog) {
        EditTokenDialog(
            onDismiss = { showDialog = showDialog.not() },
            onNegativeClick = { showDialog = showDialog.not() },
            onPositiveClick = { token, index ->
                showDialog = showDialog.not()
                tokenViewModel?.updateToken(token = token, index)
            },
            onDeleteClicked = {
                showDialog = showDialog.not()
                tokenViewModel?.deleteToken(token = it)
                trashViewModel?.addToken(it)
            },
            token = dialogToken,
            index = curSelectedIndex
        )
    }

    LazyColumn(modifier.fillMaxWidth(), state = listState) {
        itemsIndexed(tokenFeedList) { idx, item ->
            TokenFeedItem(
                modifier = Modifier,
                onItemClicked = {
                    dialogToken = it
                    curSelectedIndex = idx
                    showDialog = true
                },
                onItemLongClicked = {
                    CommonUtil.copyToClipboard(context, it)
                    Toast.makeText(context, "复制token成功！", Toast.LENGTH_SHORT).show()
                },
                tokenMixed = item,
                progress = currentProgress,
                maxProgress = 30,
                hideToken = hideTokenSelectState
            )
        }
    }

}
