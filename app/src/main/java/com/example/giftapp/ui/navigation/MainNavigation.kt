package com.example.giftapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
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
import com.example.giftapp.ui.screens.OpenGiftScreen
import com.example.giftapp.ui.screens.SendGiftScreen


sealed class BottomNavScreens(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavScreens("home", "Home", Icons.Default.Home)
    object Gallery : BottomNavScreens("gallery", "Gallery", Icons.Default.Email)
}

sealed class NavScreens(val route: String) {
    object OpenGift : NavScreens("open_gift")
    object SendGift : NavScreens("send_gift")
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
            startDestination = BottomNavScreens.Home.route,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(BottomNavScreens.Home.route) {
                HomeScreen()
            }
            composable(BottomNavScreens.Gallery.route) {
                GalleryScreen()
            }

            composable(NavScreens.OpenGift.route) {
                OpenGiftScreen()
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
        BottomNavScreens.Home,
        BottomNavScreens.Gallery
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

