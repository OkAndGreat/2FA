package widget

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twofa.R
import com.example.twofa.utils.Constant

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onItemClicked: ((Int) -> Unit)
) {
    Row(
        modifier = modifier
            .background(Color(249, 249, 249))
    ) {
        Column(
            Modifier
                .clickable(indication = null, interactionSource = remember {
                    MutableInteractionSource()
                }) {
                    onItemClicked.invoke(0)
                }
                .weight(1f)
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val iconSelectBgAnim =
                animateIntAsState(
                    targetValue = if (selectedIndex == 0) 255 else 0, label = "",
                    animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
                )
            val iconSelectBgColor = Color(248, 226, 228, iconSelectBgAnim.value)

            val image: Painter = painterResource(id = R.drawable.navigation_home)
            Icon(
                modifier = Modifier
                    .background(
                        iconSelectBgColor,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(vertical = 5.dp, horizontal = 18.dp)
                    .size(24.dp),
                painter = image,
                contentDescription = "Example Vector Image",
                tint = if (selectedIndex == 0) Constant.COMMON_RED_COLOR_DEEP else Constant.COMMON_GRAY
            )


            Text(
                text = "Tokens",
                fontSize = 12.sp,
                color = if (selectedIndex == 0) Constant.COMMON_RED_COLOR_DEEP else Constant.COMMON_GRAY
            )
        }
        Column(
            Modifier
                .clickable(indication = null, interactionSource = remember {
                    MutableInteractionSource()
                }) {
                    onItemClicked.invoke(1)
                }
                .weight(1f)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 从资源中加载矢量图形
            val image: Painter = painterResource(id = R.drawable.navigation_setting)

            val iconSelectBgAnim =
                animateIntAsState(
                    targetValue = if (selectedIndex == 1) 255 else 0,
                    label = "",
                    animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
                )
            val iconSelectBgColor = Color(248, 226, 228, iconSelectBgAnim.value)

            Icon(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(
                        iconSelectBgColor,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(vertical = 5.dp, horizontal = 18.dp)
                    .size(24.dp),
                painter = image,
                contentDescription = "Example Vector Image",
                tint = if (selectedIndex == 1) Constant.COMMON_RED_COLOR_DEEP else Constant.COMMON_GRAY
            )

            Text(
                text = "Settings",
                fontSize = 12.sp,
                color = if (selectedIndex == 1) Constant.COMMON_RED_COLOR_DEEP else Constant.COMMON_GRAY
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationPreview() {
    BottomNavigation(
        modifier = Modifier
            .height(76.dp)
            .fillMaxWidth(),
        onItemClicked = {}
    )
}

val navItemList = listOf(NavItem.TokenNavItem, NavItem.SettingNavItem)

sealed class NavItem(val route: String = "") {
    data object TokenNavItem : NavItem("token")

    data object SettingNavItem : NavItem("setting")

    data object SecureNavItem : NavItem("secure")

    data object SetPinNavItem : NavItem("set_pin")

    data object ConfirmPinNavItem : NavItem("confirm_pin")

}