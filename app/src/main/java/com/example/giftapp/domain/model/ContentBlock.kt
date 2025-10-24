package com.example.giftapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ContentBlock {
    val order: Int
}

@Serializable
@SerialName("header")
data class HeaderBlock(
    override val order: Int = 0,
    val text: String = ""
) : ContentBlock

@Serializable
@SerialName("text_body")
data class TextBlock(
    override val order: Int = 0,
    val text: String = ""
) : ContentBlock

@Serializable
@SerialName("image")
data class ImageBlock(
    override val order: Int = 0,
    val url: String = "",
    val caption: String = ""
) : ContentBlock

@Serializable
@SerialName("video")
data class VideoBlock(
    override val order: Int = 0,
    val url: String = ""
) : ContentBlock

@Serializable
@SerialName("footer")
data class FooterBlock(
    override val order: Int = 0,
    val text: String = ""
) : ContentBlock