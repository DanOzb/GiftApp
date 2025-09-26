package com.project.ui.screens

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.project.data.local.Gift
import androidx.compose.ui.unit.dp


/**
 * Screen composable that uses ReusableGrid
 */
@Composable
fun PicturesScreen(pictures: List<Gift>, onClickGift: (Gift) -> Unit){
    ReusableGrid(
        items = pictures,
        onItemClick = onClickGift
    ) { picture ->
        AsyncImage(
            model = picture.content,
            contentDescription = picture.title,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}