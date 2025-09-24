package com.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


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