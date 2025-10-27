package com.example.giftapp.viewmodel


import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.core.net.toUri

@OptIn(UnstableApi::class)
@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: ExoPlayer,
    private val downloadManager: DownloadManager,
) : ViewModel() {

    init {
        player.prepare()
        downloadManager.resumeDownloads()
    }

    fun playMedia(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        if(player.currentMediaItem == null || player.currentMediaItem != mediaItem)
            player.setMediaItem(mediaItem)
        player.play()
    }

    fun pauseMedia(){
        player.pause()
    }

    fun downloadVideo(url: String) {
        val downloadRequest = DownloadRequest.Builder(url, url.toUri()).build()
        downloadManager.addDownload(downloadRequest)
    }

    fun addDownloadListener(listener: DownloadManager.Listener) {
        downloadManager.addListener(listener)
    }

    fun removeDownloadListener(listener: DownloadManager.Listener) {
        downloadManager.removeListener(listener)
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
        downloadManager.pauseDownloads()
    }
}