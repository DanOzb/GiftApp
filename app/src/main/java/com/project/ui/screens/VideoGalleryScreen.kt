package com.project.ui.screens

import androidx.compose.runtime.Composable
import com.project.data.local.Gift

/**
 * Screen composable that uses ReusableGrid
 *
 * TODO: Add thumbnails to display each video.
 */
@Composable
fun VideosScreen(videos: List<Gift>, onClickGift: (Gift) -> Unit){
    ReusableGrid(
        items = videos,
        onItemClick = onClickGift
    ) { video ->
        //TODO: Add exoplayer
    }
}