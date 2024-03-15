package com.example.twofa.ui.token

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.twofa.ParseQRCodeEvent
import com.example.twofa.R
import com.example.twofa.ui.token.search.SearchBar
import com.example.twofa.ui.token.search.SearchDisplay
import com.example.twofa.ui.token.search.rememberSearchState
import com.example.twofa.ui.token.widget.AddButton
import com.example.twofa.ui.token.widget.NoResultWidget
import com.example.twofa.ui.token.widget.TokenFeedWidget
import com.example.twofa.utils.Constant.REQUEST_CODE_SCAN
import com.example.twofa.utils.LogUtil
import com.example.twofa.viewmodel.TokenViewModel
import com.king.camera.scan.util.LogUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TokenScreen() {
    Column(
        modifier = Modifier
            .padding(bottom = 90.dp)
            .background(Color.White)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val tokenViewModel: TokenViewModel = TokenViewModel.get(context) ?: return

        val tokenList by tokenViewModel.tokenList.collectAsState()
        val searchState = rememberSearchState(
            tokenList,
            timeoutMillis = 1000
        ) { query: TextFieldValue ->
            tokenViewModel.getTokenListByQuery(query.text)
        }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        val dispatcher: OnBackPressedDispatcher =
            LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!searchState.focused) {
                        isEnabled = false
                        Toast.makeText(context, "后退", Toast.LENGTH_SHORT).show()
                        dispatcher.onBackPressed()
                    } else {
                        searchState.query = TextFieldValue("")
                        searchState.focused = false
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                }

            }
        }
        DisposableEffect(dispatcher) { // dispose/relaunch if dispatcher changes
            dispatcher.addCallback(backCallback)
            onDispose {
                backCallback.remove() // avoid leaks!
            }
        }


        //上部区域，包括搜索框和添加按钮
        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {

            SearchBar(
                query = searchState.query,
                onQueryChange = { searchState.query = it },
                onSearchFocusChange = { searchState.focused = it },
                onClearQuery = { searchState.query = TextFieldValue("") },
                onBack = { searchState.query = TextFieldValue("") },
                searching = searchState.searching,
                focused = searchState.focused,
                modifier = Modifier.padding(end = 20.dp)
            )

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

        //下部区域，根据state.searchDisplay会变化
        when (searchState.searchDisplay) {
            SearchDisplay.InitialResults,
            SearchDisplay.Suggestions,
            SearchDisplay.SearchInProgress,
            SearchDisplay.Results -> {
                TokenFeedWidget(tokenFeedList = tokenList)
            }

            SearchDisplay.NoResults -> {
                NoResultWidget()
            }
        }
    }
}


@Composable
@Preview
fun TokenScreenPreview() {
    TokenScreen()
}