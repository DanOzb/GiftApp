package com.example.giftapp.ui.blocks

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.giftapp.domain.model.TextBlock

@Composable
fun TextBlockItem(
    block: TextBlock,
    modifier: Modifier = Modifier
) {
    Text(
        text = block.text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.fillMaxWidth()
    )
}