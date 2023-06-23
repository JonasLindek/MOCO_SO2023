package com.datojo.socialpet.View.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.datojo.socialpet.Background
import com.datojo.socialpet.ViewModel.PetStatus
import com.datojo.socialpet.View.overlays.MenuOverlay
import com.datojo.socialpet.R
import com.datojo.socialpet.View.CatAnimation
import com.datojo.socialpet.View.overlays.CatInteraction
import com.datojo.socialpet.View.theme.SocialPetTheme
import com.datojo.socialpet.ViewModel.Inventory

@Composable
fun HomeScreen(friendListNav: () -> Unit, arcadeNav: () -> Unit, mallNav: () -> Unit, stats: PetStatus, inventory: Inventory) {
    SocialPetTheme {
        Background(
            R.drawable.bedroom, "Bedroom",
            Modifier
                .scale(3.8f)
                .padding(0.dp, 275.dp)
        )
        CatAnimation()
        MenuOverlay(
            listOf(friendListNav, arcadeNav, mallNav),
            listOf("Friends", "Arcade", "Mall"),
            stats
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Bottom)
        ) {
            CatInteraction({
                if(!inventory.isEmptyFood()) {
                    stats.feed()
                    inventory.subFood()
                } }, R.drawable.foodbowl)
            CatInteraction({
                if(!inventory.isEmptyWater()) {
                    stats.drink()
                    inventory.subWater()
                } }, R.drawable.waterbowl)
        }
    }
}