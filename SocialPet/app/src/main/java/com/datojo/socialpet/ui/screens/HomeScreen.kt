package com.datojo.socialpet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.datojo.socialpet.Background
import com.datojo.socialpet.CatAnimation
import com.datojo.socialpet.CatInteraction
import com.datojo.socialpet.ui.overlays.MenuOverlay
import com.datojo.socialpet.R
import com.datojo.socialpet.StatsViewModel
import com.datojo.socialpet.ui.Screen
import com.datojo.socialpet.ui.theme.SocialPetTheme

@Composable
fun HomeScreen(navController : NavController, stats: StatsViewModel) {
    SocialPetTheme {
        Background(
            R.drawable.bedroom, "Bedroom",
            Modifier
                .scale(3.8f)
                .padding(0.dp, 275.dp)
        )
        CatAnimation()
        MenuOverlay(
            listOf(
                { navController.navigate(Screen.FriendListScreen.route) },
                { navController.navigate(Screen.ArcadeScreen.route) },
                { navController.navigate(Screen.MallScreen.route) }
            ),
            listOf(
                "Friends",
                "Arcade",
                "Mall"
            ),
            stats
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            CatInteraction {stats.feed()}
            CatInteraction {stats.pet()}
        }
    }
}