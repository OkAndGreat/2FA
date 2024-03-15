package com.example.twofa.ui.token.widget

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
import com.example.twofa.db.Token
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun TokenFeedWidget(modifier: Modifier = Modifier, tokenFeedList: List<Token>) {

    var currentProgress by remember {
        mutableStateOf(0)
    }
    val listState = rememberLazyListState()

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

    LazyColumn(modifier.fillMaxWidth(), state = listState) {
        itemsIndexed(tokenFeedList) { _, item ->
            TokenFeedItem(
                modifier = Modifier,
                onItemClicked = {

                },
                platformName = item.platformName,
                userName = item.userName,
                secret = item.secretKey,
                progress = currentProgress,
                maxProgress = 30
            )
        }
    }

}
