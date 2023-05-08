package com.datojo.socialpet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class AnimationState {
    IDLE,
    LAYDOWN,
    LAYING,
    STANDUP
}

@Composable
fun CatAnimation() {
    // Load the animation frames as drawables
    val idleFrameIds = listOf(
        R.drawable.cat_1_idle___1_,
        R.drawable.cat_1_idle___2_,
        R.drawable.cat_1_idle___3_,
        R.drawable.cat_1_idle___4_,
        R.drawable.cat_1_idle___5_,
        R.drawable.cat_1_idle___6_,
        R.drawable.cat_1_idle___7_,
        R.drawable.cat_1_idle___8_,
        R.drawable.cat_1_idle___9_,
        R.drawable.cat_1_idle___10_
        // add more frames as needed
    )

    val laydownFrameIds = listOf(
        R.drawable.cat_1_laydown__1_,
        R.drawable.cat_1_laydown__2_,
        R.drawable.cat_1_laydown__3_,
        R.drawable.cat_1_laydown__4_,
        R.drawable.cat_1_laydown__5_,
        R.drawable.cat_1_laydown__6_,
        R.drawable.cat_1_laydown__7_,
        R.drawable.cat_1_laydown__8_
    )

    val layingFrameIds = listOf(
        R.drawable.cat_1_laydown__8_
    )

    val standupFrameIds = listOf(
        R.drawable.cat_1_laydown__8_,
        R.drawable.cat_1_laydown__7_,
        R.drawable.cat_1_laydown__6_,
        R.drawable.cat_1_laydown__5_,
        R.drawable.cat_1_laydown__4_,
        R.drawable.cat_1_laydown__3_,
        R.drawable.cat_1_laydown__2_,
        R.drawable.cat_1_laydown__1_
    )

    val transitionalAnimations = listOf(
        AnimationState.LAYDOWN,
        AnimationState.STANDUP
    )

    var frameIds by remember { mutableStateOf(idleFrameIds) }

    // Define the current frame index
    var frameIndex by remember { mutableStateOf(0) }
    var animationState by remember { mutableStateOf(AnimationState.IDLE) }
    var animationChange by remember { mutableStateOf(false) }

    var highestFrameIndex by remember { mutableStateOf(0) }
    var currentFrameIndex by remember { mutableStateOf(0) }

    // Define a timer to change the frame every 100ms
    LaunchedEffect(Unit) {
        while (true) {

            if (animationChange) {
                animationChange = false
                frameIndex = 0

                frameIds = when (animationState) {
                    AnimationState.IDLE -> idleFrameIds
                    AnimationState.LAYDOWN -> laydownFrameIds
                    AnimationState.LAYING -> layingFrameIds
                    AnimationState.STANDUP -> standupFrameIds
                }
                highestFrameIndex = frameIds.size-1
                currentFrameIndex = 0
            }


            frameIndex = (frameIndex + 1) % frameIds.size // cycle to the next frame
            if (currentFrameIndex <= highestFrameIndex) currentFrameIndex++
            delay(100) // wait for 100ms

            if (currentFrameIndex == highestFrameIndex) {
                if (animationState == AnimationState.LAYDOWN) {
                    animationState = AnimationState.LAYING
                    animationChange = true
                }
                else if (animationState == AnimationState.STANDUP) {
                    animationState = AnimationState.IDLE
                    animationChange = true
                }
            }
        }
    }

    // Draw the current frame of the animation
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                if (transitionalAnimations.contains(animationState)) {
                    return@clickable // Don't change the animation when it's in a transitional state
                }
                if (animationState == AnimationState.IDLE) {
                    animationState = AnimationState.LAYDOWN
                } else {
                    animationState = AnimationState.STANDUP
                }
                animationChange = true },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        PixelArtImage(resId = frameIds[frameIndex], scale = 15f)
    }

}

@Composable
fun PixelArtImage(@DrawableRes resId: Int, scale: Float) {
    val bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, resId)
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt(), false)
    Image(
        bitmap = scaledBitmap.asImageBitmap(),
        contentDescription = null,
        modifier = Modifier.offset(20.dp, 230.dp)
    )
}