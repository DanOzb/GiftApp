package com.project.data.local

import ContentBlock
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

/**
 *
 * Local database for opened gifts. Will be shown in grid layout.
 *
 */

@Entity(tableName = "gifts")
data class GiftEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val sender: String,
    val timestamp: String,
    val favorite: Boolean = false,
    val contentBlocks: List<ContentBlock>,
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

@Database(entities = [GiftEntity::class, Tag::class, GiftTag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}