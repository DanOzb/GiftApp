package com.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.data.local.Gift


/**
 * Screen composable that uses ReusableGrid
 */
@Composable
fun LettersScreen(
    letters: List<Gift>,
    onClickGift: (Gift) -> Unit){

    ReusableGrid(
        letters,
        onItemClick = onClickGift
    ) { letter ->
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(letter.title, color = Color.White)
        }
    }
}

