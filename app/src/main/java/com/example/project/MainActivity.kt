package com.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Enumeration for the different types of gifts available.
 *
 * @property LETTER For a text-based gift/message
 * @property PICTURE For an image-based gift
 * @property VIDEO For a video-based gift
 */
enum class GiftType {
    LETTER, PICTURE, VIDEO
}

/**
 * Sealed class representing the different screens for bottom navigation.
 *
 * @property route The navigation for the screen
 * @property title The title for the screen
 * @property icon The icon for the screen
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Letters : Screen("letters", "Letters", Icons.Default.Email)
    object Pictures : Screen("pictures", "Pictures", Icons.Default.Face)
    object Videos : Screen("videos", "Videos", Icons.Default.PlayArrow)
}

/**
 * Main navigation composable that sets up the app's navigation structure.
 * Manages and handles bottom navigation bar visibility and navigation between screens.
 *
 * open_letter, open_picture, open_video routes hides navigation bar for full screen purposes.
 */
@Composable
fun MainNavigation(){
    val navController = rememberNavController()

    val screens = listOf(
        Screen.Home,
        Screen.Letters,
        Screen.Pictures,
        Screen.Videos,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val hideBottomBar = currentDestination?.route in listOf("open_letter", "open_picture", "open_video")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(!hideBottomBar){
                BottomAppBar {
                    screens.forEach { screen ->
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                        )
                    }
                }
            }

        }
    ) {
        innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.then(
                    if(hideBottomBar){
                        Modifier.padding(0.dp).fillMaxSize()
                    } else{
                        Modifier.padding(innerPadding).fillMaxSize()
                    }
                )
            ){
                composable(Screen.Home.route) {
                    HomeScreen(navController)
                }
                composable(Screen.Letters.route) {
                    LettersScreen()
                }
                composable(Screen.Pictures.route) {
                    PicturesScreen()
                }
                composable(Screen.Videos.route) {
                    VideosScreen()
                }
                composable("open_letter") {
                    NewLetterScreen()
                }
                composable("open_picture") {
                    NewPictureScreen()
                }
                composable("open_video") {
                    NewVideoScreen()
                }
            }
    }
}

/**
 * Home screen composable that displays the default screen when opening the app.
 * Users can tap on different icons to navigate to specific gift opening screens.
 *
 * @param navController The navigation controller used to navigate between screens
 */
@Composable
fun HomeScreen(navController: NavController){

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Image(
            imageVector = Icons.Default.Email,
            contentDescription = "Letter Icon",
            modifier = Modifier.clickable(onClick = {
                navController.navigate("open_letter")
            }).size(40.dp)
        )
        Image(
            imageVector = Icons.Default.Face,
            contentDescription = "Picture Icon",
            modifier = Modifier.clickable(onClick = {
                navController.navigate("open_picture")
            }).size(40.dp)
        )
        Image(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Video Icon",
            modifier = Modifier.clickable(onClick = {
                navController.navigate("open_video")
            }).size(40.dp)
        )
    }
}

/**
 * Screen composable for displaying a newly opened letter gift.
 * Currently shows a placeholder cyan background.
 *
 */
@Composable
fun NewLetterScreen(){
    //TODO: get random locked letter gift
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    )
}

/**
 * Screen composable for displaying a newly opened image gift.
 * Currently shows a placeholder cyan background.
 *
 */
@Composable
fun NewPictureScreen(){
    //TODO: get random locked picture gift
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    )
}

/**
 * Screen composable for displaying a newly opened video gift.
 * Currently shows a placeholder cyan background.
 *
 */
@Composable
fun NewVideoScreen(){
    //TODO: get random locked video gift
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    )
}

/**
 * Screen composable that calls LazyGrid with gift type letter
 */
@Composable
fun LettersScreen(){
    LazyGrid(GiftType.LETTER)
}

/**
 * Screen composable that calls LazyGrid with gift type picture
 */
@Composable
fun PicturesScreen(){
    LazyGrid(GiftType.PICTURE)
}

/**
 * Screen composable that calls LazyGrid with gift type video
 */
@Composable
fun VideosScreen(){
    LazyGrid(GiftType.VIDEO)
}

/**
 * Reusable composable that displays a lazy vertical grid depending on the gift type.
 *
 * Currently uses dummy data for testing purposes.
 *
 * @param gift The type of gift to display in the grid.
 */
@Composable
fun LazyGrid(gift: GiftType){
    //Test lists
    //TODO: Change dummy letters, videos and pictures into cached ones
    val list = (1..40).map {it.toString()}

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {

            if(gift == GiftType.LETTER){
                items(list.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = list[index],
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else if(gift == GiftType.PICTURE){
                items(list.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.DarkGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Image placeholder", color = Color.White)
                        }

                    }
                }
            }
            else {
                items(list.size){ index ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.DarkGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Video placeholder", color = Color.White)
                        }
                    }
                }
            }
        }
    )
}

/**
 * Main Activity class that serves as the entry point for the application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainNavigation()
        }
    }
}

