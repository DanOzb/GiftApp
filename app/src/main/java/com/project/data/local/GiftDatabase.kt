package com.project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.domain.model.GiftType
import java.sql.Timestamp

/**
 *
 * Local database for opened gifts. Will be shown in grid layout.
 *
 */

@Entity(tableName = "gifts")
data class Gift(
    @PrimaryKey val id: Int,
    val type: GiftType,
    val content: String,
    val favorite: Boolean,
    val timestamp: Timestamp
)

@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey val id: Int,
    val name: String
)

@Entity(tableName = "gift_tags", primaryKeys = ["giftId", "tagId"] )
data class GiftTag(
    val giftId: Int,
    val tagId: Int
)