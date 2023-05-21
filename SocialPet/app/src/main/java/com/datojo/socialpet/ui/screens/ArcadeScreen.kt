package com.datojo.socialpet.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.datojo.socialpet.Background
import com.datojo.socialpet.R
import com.datojo.socialpet.StatsViewModel
import com.datojo.socialpet.ui.Screen
import com.datojo.socialpet.ui.overlays.MenuOverlay
import com.datojo.socialpet.ui.theme.SocialPetTheme

@Composable
fun ArcadeScreen(navController: NavController, stats: StatsViewModel) {
    SocialPetTheme {
        Background(
            R.drawable.arcade, "Arcade",
            Modifier
                .scale(3.5f)
                .fillMaxSize()
                .padding(0.dp, 275.dp)
        )
        MenuOverlay(
            listOf(
                { navController.navigate(Screen.FriendListScreen.route) },
                { navController.navigate(Screen.HomeScreen.route) },
                { navController.navigate(Screen.MallScreen.route) }
            ),
            listOf(
                "Friends",
                "Home",
                "Mall"
            ),
            stats
        )
    }
}