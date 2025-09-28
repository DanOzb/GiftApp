package com.project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.domain.model.GridItem


/**
 * Reusable composable that displays a lazy vertical grid depending on the gift type.
 *
 * @param gift The type of gift to display in the grid.
 */


@Composable
fun <T: GridItem> ReusableGrid(
    items: List<T>,
    onItemClick: (T) -> Unit,
    itemContent: @Composable (T) -> Unit
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
                itemContent(items[index])
            }
        }
    }
}
