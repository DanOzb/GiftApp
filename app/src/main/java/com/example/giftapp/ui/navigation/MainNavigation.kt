package com.example.giftapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.giftapp.ui.screens.GalleryScreen
import com.example.giftapp.ui.screens.HomeScreen
import com.example.giftapp.ui.screens.SendGiftScreen


sealed class NavScreens(val route: String, val title: String, val icon: ImageVector) {
    object Home : NavScreens("home", "Home", Icons.Default.Home)
    object Gallery : NavScreens("gallery", "Gallery", Icons.Default.PlayArrow)
    object SendGift : NavScreens("send_gift", "Send Gift", Icons.Default.Email)
}


@Composable
fun MainNavigation(
    modifier: Modifier
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavScreens.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(NavScreens.Home.route) {
                HomeScreen(navController)
            }
            composable(NavScreens.Gallery.route) {
                GalleryScreen()
            }

            composable(NavScreens.SendGift.route) {
                SendGiftScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val screens = listOf(
        NavScreens.Home,
        NavScreens.Gallery,
        NavScreens.SendGift
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

