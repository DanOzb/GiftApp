package com.example.project.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlin.collections.contains


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
