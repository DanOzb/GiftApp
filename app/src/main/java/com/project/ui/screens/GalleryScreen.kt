package com.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.data.local.GiftEntity


/**
 * Screen composable that uses ReusableGrid
 */
@Composable
fun GalleryScreen(
    letters: List<GiftEntity>,
    onClickGift: (GiftEntity) -> Unit){
}

@Composable
fun ReusableGrid(
    items: List<GiftEntity>,
    onItemClick: (GiftEntity) -> Unit,
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(items.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onItemClick(items[index]) }
                    .padding(8.dp)
            ) {
                //Item content
            }
        }
    }
}