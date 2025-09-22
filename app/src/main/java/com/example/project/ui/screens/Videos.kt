package com.example.project.ui.screens

import androidx.compose.runtime.Composable
import com.example.project.domain.model.GiftType

/**
 * Screen composable that calls LazyGrid with gift type video
 */
@Composable
fun VideosScreen(){
    LazyGrid(GiftType.VIDEO)
}