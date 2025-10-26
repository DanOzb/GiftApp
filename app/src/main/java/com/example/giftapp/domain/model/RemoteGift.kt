package com.example.giftapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteGift(
    @SerialName("giftId")
    val id: String = "",
    val title: String = "",
    val sender: String = "",
    val timestamp: Long = 0,
    val contentBlocks: List<@Serializable ContentBlock> = emptyList()
)