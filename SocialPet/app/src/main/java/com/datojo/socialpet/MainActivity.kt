package com.datojo.socialpet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.datojo.socialpet.ui.theme.SocialPetTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object FriendScreen : Screen("friend_screen")
    object SettingsScreen : Screen("settings_screen")
    object ArcadeScreen : Screen("arcade_screen")
    object MallScreen : Screen("mall_screen")

    fun withArguments(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.FriendScreen.route) {
            FriendListScreen(navController = navController)
        }
    }
}

@Composable
fun HomeScreen(navController : NavController) {
    SocialPetTheme {
        MenuOverlay { navController.navigate(Screen.FriendScreen.route) }
        MyAnimation()
    }
}

@Composable
fun FriendListScreen(navController: NavController) {
    val contacts = listOf("Daniel Sonnenberg", "Tom Küper", "Jonas Lindek")

    SocialPetTheme {
        ListOverlay(contacts, { navController.navigate(Screen.HomeScreen.route) }, { 1 + 1})
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SocialPetTheme {
        Greeting("Android")
    }
}

@Composable
fun MenuOverlay(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        // TODO: Healthbars
        StatusBar(Color.Red, 0.5f)
        StatusBar(Color.Green, 0.75f)
        StatusBar(Color(.2f,.4f,1f), 1f)
        Row() {
            MenuButton(onClick)
        }
    }
}

@Composable
fun ListOverlay(contacts: List<String>, onClick: () -> Unit, addContact: () -> Unit) {
    ContactBar(onClick)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        ContactList(contacts)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        AddButton(addContact)
    }
}

enum class AnimationState {
    IDLE,
    LAYDOWN,
    LAYING,
    STANDUP
}

@Composable
fun MyAnimation() {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(onClick = {
            if (animationState == AnimationState.IDLE) {
                animationState = AnimationState.LAYDOWN
            } else {
                animationState = AnimationState.STANDUP
            }
            animationChange = true }) {
            Text("${animationState.name}")
        }
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
    )
}

@Composable
fun StatusBar(color: Color, state: Float) {
    Row(
        modifier = Modifier.padding(0.dp, 4.dp)
    ) {
        Image(
            painter= painterResource(id = R.drawable.heart),
            contentDescription = "Heart",
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .zIndex(1f)
        )
        LinearProgressIndicator(
            progress = state,
            color = color,
            modifier = Modifier
                .width(54.dp)
                .padding(0.dp, 12.dp)
                .height(12.dp)
                .absoluteOffset((-10).dp, (-4).dp)
                .border(2.dp, Color.Black, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Composable
fun MenuButton(onClick: () -> Unit) {
    Row (){
        FloatingActionButton(
            onClick = onClick ,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(75.dp)
                .height(75.dp)
        ) {

        }
    }
}

@Composable
fun Contact(name: String) {
    val hex = java.lang.String.format("#%06x", 0xFFFFFF and name.hashCode())

    Row(
        modifier = Modifier
            .drawBehind {
                val borderSize = 2.dp.toPx()
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(android.graphics.Color.parseColor(hex)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Text(text = name)
        }
    }

}
@Composable
fun ContactList(contacts: List<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(contacts) { contacts->
            Contact(contacts)
        }
    }
}

@Composable
fun ContactBar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            BackButton(onClick)
        }
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ){


            Text(
                text = "Contacts",
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Row (){
        FloatingActionButton(
            onClick = onClick ,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        ) {

        }
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    Row () {
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = Color.White,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        ) {

        }
    }
}