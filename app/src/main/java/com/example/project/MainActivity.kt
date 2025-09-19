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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


enum class GiftType {
    LETTER, PICTURE, VIDEO
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Letters : Screen("letters", "Letters", Icons.Default.Email)
    object Pictures : Screen("pictures", "Pictures", Icons.Default.Face)
    object Videos : Screen("videos", "Videos", Icons.Default.PlayArrow)
}

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
            })
        )
        Image(
            imageVector = Icons.Default.Face,
            contentDescription = "Picture Icon",
            modifier = Modifier.clickable(onClick = {
                navController.navigate("open_picture")
            })
        )
        Image(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Video Icon",
            modifier = Modifier.clickable(onClick = {
                navController.navigate("open_video")
            })
        )
    }
}

@Composable
fun NewLetterScreen(){
    //TODO: get random locked letter gift
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    )
}
@Composable
fun NewPictureScreen(){
    //TODO: get random locked picture gift
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    )
}
@Composable
fun NewVideoScreen(){
    //TODO: get random locked video gift
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    )
}

@Composable
fun LettersScreen(){
    LazyGrid(GiftType.LETTER)
}
@Composable
fun PicturesScreen(){
    LazyGrid(GiftType.PICTURE)
}
@Composable
fun VideosScreen(){
    LazyGrid(GiftType.VIDEO)
}

/*
    TODO: Change dummy letters, videos and pictures into cached ones
 */
@Composable
fun LazyGrid(gift: GiftType){
    //Test lists
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MainNavigation()
        }
    }
}

