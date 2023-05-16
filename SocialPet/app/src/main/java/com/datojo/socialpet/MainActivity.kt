package com.datojo.socialpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.datojo.socialpet.ui.theme.SocialPetTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}


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


//Navigation by Navcontroller
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.FriendListScreen.route) {
            FriendListScreen(navController = navController)
        }
        composable(route = Screen.ArcadeScreen.route) {
            ArcadeScreen(navController = navController)
        }
        composable(route = Screen.MallScreen.route) {
            MallScreen(navController = navController)
        }
    }
}


//Composables listing all needed parts for the different Screens
@Composable
fun HomeScreen(navController : NavController) {
    SocialPetTheme {
        Background(R.drawable.bedroom, "Bedroom",
            Modifier
                .scale(3.8f)
                .padding(0.dp, 275.dp)
        )
        CatAnimation()
        MenuOverlay({ navController.navigate(Screen.FriendListScreen.route) },
            listOf(

                { navController.navigate(Screen.ArcadeScreen.route) },
                { navController.navigate(Screen.MallScreen.route) }
            ),
            listOf(
                "Arcade",
                "Mall"
            )
        )
    }
}

@Composable
fun FriendListScreen(navController: NavController) {
    val contacts = listOf("Daniel Sonnenberg", "Tom Küper", "Jonas Lindek")
    SocialPetTheme {
        ListOverlay(contacts, { navController.popBackStack() }, { 1 + 1})
    }
}

@Composable
fun ArcadeScreen(navController: NavController) {
    SocialPetTheme {
        Background(R.drawable.arcade, "Arcade",
            Modifier
                .scale(3.8f)
                .fillMaxSize()
                .padding(0.dp, 275.dp)
        )
        MenuOverlay({ navController.navigate(Screen.FriendListScreen.route) },
            listOf(
                { navController.navigate(Screen.HomeScreen.route) },
                { navController.navigate(Screen.MallScreen.route) }
            ),
            listOf(
                "Home",
                "Mall"
            )
        )
    }
}

@Composable
fun MallScreen(navController: NavController) {
    SocialPetTheme {
        Background(R.drawable.mall, "Mall",
            Modifier
                .scale(3.8f)
                .fillMaxSize()
                .padding(0.dp, 275.dp)
        )
        MenuOverlay({ navController.navigate(Screen.FriendListScreen.route) },
            listOf(
                { navController.navigate(Screen.HomeScreen.route) },
                { navController.navigate(Screen.ArcadeScreen.route) }
            ),
            listOf(
                "Home",
                "Arcade"
            )
        )
    }
}

@Composable
fun Background(id: Int, description: String, modifier: Modifier) {
    Image(
        painter = painterResource(id = id),
        contentDescription = description,
        modifier = modifier
    )
}


//Overlays to add to the Screens
@Composable
fun MenuOverlay(friendRoute: () -> Unit, screenRoutes: List<() -> Unit>, screenNames: List<String>) {
    var menuPopUpControl by remember { mutableStateOf(false) }
    var notificationPopUpControl by remember { mutableStateOf(false) }
    var audioPopUpControl by remember { mutableStateOf(false) }
    var aboutPopUpControl by remember { mutableStateOf(false) }

    if(menuPopUpControl)
        MenuPopUp({ change -> menuPopUpControl = change }, friendRoute, screenRoutes, screenNames)

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
        Row {
            MenuButton { change -> menuPopUpControl = change }
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


//Composable Parts to add to the overlays
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
fun MenuPopUp(onDismiss: (Boolean) -> Unit, friendRoute: () -> Unit, screenRoutes: List<() -> Unit>, screenNames: List<String>){
    Column(
        modifier = Modifier
            .background(Color.LightGray.copy(alpha = .5f))
    ) {
        Popup(
            onDismissRequest = { onDismiss(false) },
            properties = PopupProperties(
                focusable = true,
                dismissOnClickOutside = true,

            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(100.dp, 170.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray.copy(alpha = .5f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                Spacer(Modifier.padding(6.dp))
                Text(
                    text = "Go Somewhere",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                PopUpButton(onClick = friendRoute, name = "Friends")

                PopUpButton(onClick = screenRoutes[0], name = screenNames[0])

                PopUpButton(onClick = screenRoutes[1], name = screenNames[1])

                PopUpButton(onClick = { /*TODO*/ }, name = "Settings")
            }
        }
    }
}

@Composable
fun SettingsPopUp() {

}

@Composable
fun NotificationPopUp() {

}

@Composable
fun AboutPopUp() {

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
        ){
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


//Button Composables
@Composable
fun MenuButton(onClick: (Boolean) -> Unit) {
    Row(){
        FloatingActionButton(
            onClick = { onClick(true) } ,
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

@Composable
fun PopUpButton(onClick: () -> Unit, name: String) {
    Row() {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(12.dp, 6.dp),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray.copy(alpha = .7f)),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = name,
                color = Color.White
            )
        }
    }
}