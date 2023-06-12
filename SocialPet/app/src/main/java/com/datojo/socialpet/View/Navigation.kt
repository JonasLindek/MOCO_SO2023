package com.datojo.socialpet.View

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.datojo.socialpet.ViewModel.PetStatus
import com.datojo.socialpet.View.screens.ArcadeScreen
import com.datojo.socialpet.View.screens.FriendListScreen
import com.datojo.socialpet.View.screens.HomeScreen
import com.datojo.socialpet.View.screens.MallScreen

//Class to simplify Navigation by storing the route names and adding a feature to add arguments
sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object FriendListScreen : Screen("friend_screen")
    object ArcadeScreen : Screen("arcade_screen")
    object MallScreen : Screen("mall_screen")

    fun withArguments(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}

//Navigation by NavController
@Composable
fun Navigation(stats: PetStatus, contacts: List<String>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                {navController.navigate(Screen.FriendListScreen.route)},
                {navController.navigate(Screen.ArcadeScreen.route)},
                {navController.navigate(Screen.MallScreen.route)},
                stats)
        }
        composable(route = Screen.FriendListScreen.route) {
            FriendListScreen(
                {navController.popBackStack()},
                contacts)
        }
        composable(route = Screen.ArcadeScreen.route) {
            ArcadeScreen(
                {navController.navigate(Screen.FriendListScreen.route)},
                {navController.navigate(Screen.HomeScreen.route)},
                {navController.navigate(Screen.MallScreen.route)},
                stats)
        }
        composable(route = Screen.MallScreen.route) {
            MallScreen(
                {navController.navigate(Screen.FriendListScreen.route)},
                {navController.navigate(Screen.HomeScreen.route)},
                {navController.navigate(Screen.ArcadeScreen.route)},
                stats)
        }
    }
}