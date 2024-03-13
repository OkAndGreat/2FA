package com.example.twofa.ui.token.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.twofa.db.Token

@Composable
fun TokenFeedWidget(modifier: Modifier = Modifier, tokenFeedList: List<Token>) {

    LazyColumn(modifier.fillMaxWidth()) {
        itemsIndexed(tokenFeedList) { _, item ->
            TokenFeedItem(
                modifier = Modifier,
                onItemClicked = { /*TODO*/ },
                platformName = item.platformName,
                userName = item.userName,
                token = item.secretKey,
                progress = 50,
                maxProgress = 100
            )
        }
    }

}
