package com.example.twofa

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.twofa.ui.apperance.ApperanceScreen
import com.example.twofa.ui.ioport.IOportScreen
import com.example.twofa.ui.secure.ChangePincodeScreen
import com.example.twofa.ui.secure.ConfirmPincodeScreen
import com.example.twofa.ui.secure.SecureScreen
import com.example.twofa.ui.secure.SetPinCodeScreen
import com.example.twofa.ui.setting.SettingScreen
import com.example.twofa.ui.token.TokenScreen
import com.example.twofa.ui.trash.TrashScreen
import widget.NavItem

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = NavItem.TokenNavItem.route,
        modifier = modifier
    ) {
        composable(route = NavItem.TokenNavItem.route) {
            TokenScreen()
        }
        composable(route = NavItem.SettingNavItem.route) {
            SettingScreen()
        }
        composable(route = NavItem.SecureNavItem.route) {
            SecureScreen()
        }
        composable(route = NavItem.SetPinNavItem.route) {
            SetPinCodeScreen()
        }
        composable(route = NavItem.ConfirmPinNavItem.route) {
            ConfirmPincodeScreen()
        }
        composable(route = NavItem.ChangePinNavItem.route) {
            ChangePincodeScreen()
        }
        composable(route = NavItem.TrashNavItem.route) {
            TrashScreen()
        }
        composable(route = NavItem.ApperanceNavItem.route) {
            ApperanceScreen()
        }
        composable(route = NavItem.IOportNavItem.route) {
            IOportScreen()
        }

    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }