package com.example.giftapp.ui.screen.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun AddItemMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onAddHeader: () -> Unit,
    onAddMessage: () -> Unit,
    onAddVideo: () -> Unit,
    onAddImage: () -> Unit,
    onAddAudio: () -> Unit,
    onAddFooter: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss
    ) {
        DropdownMenuItem(
            text = { Text("Header") },
            onClick = onAddHeader,
        )
        DropdownMenuItem(
            text = { Text("Message") },
            onClick = onAddMessage,
        )
        DropdownMenuItem(
            text = { Text("Video") },
            onClick = onAddVideo,
        )
        DropdownMenuItem(
            text = { Text("Image") },
            onClick = onAddImage,
        )
        DropdownMenuItem(
            text = { Text("Audio") },
            onClick = onAddAudio,
        )
        DropdownMenuItem(
            text = { Text("Footer") },
            onClick = onAddFooter,
        )
    }
}
