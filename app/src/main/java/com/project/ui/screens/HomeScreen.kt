package com.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.domain.viewModel.GiftViewModel

/**
 * Home screen composable that displays the default screen when opening the app.
 * Users can tap on different icons to navigate to specific gift opening screens.
 *
 * @param navController The navigation controller used to navigate between screens
 */
@Composable
fun HomeScreen(navController: NavController, viewModel: GiftViewModel){

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