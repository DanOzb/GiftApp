package com.example.giftapp.ui.screen.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


/**
 * Media picker button to let user choose audio, video or image
 *
 * @param mimeType - Use GiftMimeTypes to get the correct mime type
 * @param text - Text to display on the button
 */
@Composable
fun MediaPickerButton(
    mimeType: String,
    text: String,
    onMediaSelected: (String) -> Unit,
){
    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        //TODO: Change uri to downloadable url
        onMediaSelected(uri.toString())
    }

    Button(
        onClick = {
            pickMedia.launch(mimeType)
        }
    ){
        Text(text)
    }
}