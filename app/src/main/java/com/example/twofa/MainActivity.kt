package com.example.twofa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.twofa.ui.theme.TwoFATheme
import com.example.twofa.utils.StatusBarHeight
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import widget.BottomNavigation
import widget.NavItem
import widget.navItemList

class MainActivity : ComponentActivity() {
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
                        color = Color.Transparent, darkIcons = useDarkIcons
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
        NavigationHost(navHostController = navController)
        BottomNavigation(
            selectedIndex = currentSelectIndex,
            modifier = Modifier
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
