package com.project.domain.model

import ContentBlock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteGift(
    @SerialName("giftId")
    val id: Int = 0,
    val title: String = "",
    val sender: String = "",
    val timestamp: Long = 0,
    val contentBlocks: List<@Serializable ContentBlock>
)
