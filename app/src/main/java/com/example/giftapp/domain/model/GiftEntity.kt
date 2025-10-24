package com.example.giftapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gifts")
data class GiftEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val sender: String,
    val timestamp: Long,
    val favorite: Boolean = false,
    val contentBlocks: List<ContentBlock>,
)