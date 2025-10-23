package com.example.giftapp.domain.model

import android.net.Uri

sealed class GiftItem {
    abstract val id: String
    abstract val type: String

    data class Header(
        override val id: String = "header",
        val text: String = "",
        override val type: String = "header"
    ) : GiftItem()

    data class Message(
        override val id: String = java.util.UUID.randomUUID().toString(),
        val text: String = "",
        override val type: String = "message"
    ) : GiftItem()

    data class Video(
        override val id: String = java.util.UUID.randomUUID().toString(),
        val uri: Uri? = null,
        val caption: String = "",
        override val type: String = "video"

    ) : GiftItem()

    data class Image(
        override val id: String = java.util.UUID.randomUUID().toString(),
        val uri: Uri? = null,
        val caption: String = "",
        override val type: String = "image"

    ) : GiftItem()

    data class Audio(
        override val id: String = java.util.UUID.randomUUID().toString(),
        val uri: Uri? = null,
        val caption: String = "",
        override val type: String = "audio"

    ) : GiftItem()

    data class Footer(
        override val id: String = "footer",
        val text: String = "",
        override val type: String = "footer"
    ) : GiftItem()
}