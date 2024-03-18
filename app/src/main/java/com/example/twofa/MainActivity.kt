package com.example.twofa

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.twofa.ui.theme.TwoFATheme
import com.example.twofa.utils.BiometricUtil
import com.example.twofa.utils.Constant
import com.example.twofa.utils.EncryptUtil
import com.example.twofa.utils.LogUtil
import com.example.twofa.utils.StatusBarHeight
import com.example.twofa.viewmodel.GlobalViewModel
import com.example.twofa.viewmodel.SecurityViewModel
import com.example.twofa.viewmodel.TokenViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.snackbar.Snackbar
import com.king.camera.scan.CameraScan
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import widget.BottomNavigation
import widget.NavItem
import widget.navItemList

class MainActivity : FragmentActivity() {

    private val securityViewModel by lazy {
        SecurityViewModel.get(this)
    }
    private val globalViewModel by lazy {
        GlobalViewModel.get(this)
    }

    val screenCaptureCallback = ScreenCaptureCallback {
        // Add logic to take action in your app.
        if (securityViewModel?.screenshotSelectState?.value == false) {
            Toast.makeText(this, "not allowed", Toast.LENGTH_SHORT).show()
            Snackbar.make(
                globalViewModel!!.localView!!,
                "该页面不允许截图",
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(ContextCompat.getColor(this, R.color.red_deep))
                .setTextColor(ContextCompat.getColor(this, R.color.red_deep))
                .setActionTextColor(ContextCompat.getColor(this, R.color.red_deep))
                .setAction("前往设置页面修改") {
                    globalViewModel?.navController?.navigate(NavItem.SettingNavItem.route)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoFATheme {

                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !isSystemInDarkTheme()

                DisposableEffect(systemUiController, useDarkIcons) {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = Color.White, darkIcons = useDarkIcons
                    )
                    // setStatusBarColor() and setNavigationBarColor() also exist


                    onDispose {}
                }
                // 确保内容占据刘海区域等
                WindowCompat.setDecorFitsSystemWindows(window, false)

                //Window Insets是异步传递给视图的,需要确保ProvideWindowInsets放在视图根布局
                ProvideWindowInsets {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = StatusBarHeight()
                            ), color = Color.Transparent
                    ) {
                        MainApp()
                    }
                }

                globalViewModel?.localView = LocalView.current
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStart() {
        super.onStart()

        val tokenViewModel = TokenViewModel.get(this)
        tokenViewModel?.getTokenListByDb()
        if (securityViewModel?.screenshotSelectState?.value == true) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }

        // Pass in the callback created in the previous step
        // and the intended callback executor (e.g. Activity's mainExecutor).
        registerScreenCaptureCallback(mainExecutor, screenCaptureCallback)
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStop() {
        super.onStop()
        unregisterScreenCaptureCallback(screenCaptureCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val tokenViewModel = TokenViewModel.get(this)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                0x01 -> {
                    val result = CameraScan.parseScanResult(data)
                    result?.let {
                        tokenViewModel?.emitQRCodeScanEvent(ParseQRCodeEvent(it))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainApp() {
    val globalViewModel: GlobalViewModel? = GlobalViewModel.get(LocalContext.current)

    val navController = rememberNavController()
    globalViewModel?.navController = navController
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val currentSelectIndex =
        navItemList.findFirstMatchIndex(currentDestination?.route ?: "")

    Box(modifier = Modifier.fillMaxSize()) {
        NavigationHost(navHostController = navController, modifier = Modifier)
        if (currentDestination?.route == NavItem.SettingNavItem.route || currentDestination?.route == NavItem.TokenNavItem.route)
            BottomNavigation(
                selectedIndex = currentSelectIndex,
                modifier = Modifier
                    .padding(bottom = 22.dp)
                    .align(Alignment.BottomCenter)
                    .height(76.dp),
                onItemClicked = {
                    navController.navigateSingleTopTo(navItemList[it].route)
                }
            )
    }
}

fun <T : NavItem> List<T>.findFirstMatchIndex(route: String): Int {
    this.forEachIndexed { index, t -> if (t.route == route) return index }
    return 0
}

class ParseQRCodeEvent(val msg: String)
