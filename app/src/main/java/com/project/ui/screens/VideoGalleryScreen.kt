package com.project.ui.screens

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
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