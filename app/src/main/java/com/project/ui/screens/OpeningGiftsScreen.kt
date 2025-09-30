package com.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.project.domain.viewModel.PlayerViewModel


/**
 * Screen composable for displaying a newly opened letter gift.
 * Currently shows a placeholder cyan background.
 *
 */
@Composable
fun OpenLetterScreen(){
    //TODO: get new letter from remote api
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
fun OpenPictureScreen(){
    //TODO: get new letter from remote api
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
fun OpenVideoScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
    ){

    }
}