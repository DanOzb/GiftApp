package com.project.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import com.project.domain.viewModel.GiftViewModel
import com.project.domain.viewModel.PlayerViewModel
import com.project.ui.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

/**
 * Main Activity class that serves as the entry point for the application.
 *
 */

@UnstableApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: GiftViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    private val downloadListener = object : DownloadManager.Listener{

        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            if(download.state == Download.STATE_COMPLETED){
                //TODO: play when user clicks video
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainNavigation(
                viewModel = TODO(),
                playerViewModel = TODO()
            )
        }

        playerViewModel.addDownloadListener(downloadListener)
    }

    override fun onStart() {
        super.onStart()
        playerViewModel.player.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        playerViewModel.player.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        playerViewModel.removeDownloadListener(downloadListener)
    }
}