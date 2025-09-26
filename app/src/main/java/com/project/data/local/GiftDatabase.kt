package com.project.data.local

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase
import com.project.domain.model.GiftType
import com.project.domain.model.GridItem
import java.sql.Timestamp

/**
 *
 * Local database for opened gifts. Will be shown in grid layout.
 *
 */

@Entity(tableName = "gifts")
data class Gift(
    @PrimaryKey(autoGenerate = true) override val id: Int = 0,
    override val title: String,
    val type: GiftType,
    override val content: String,
    val favorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
) : GridItem

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

@Database(entities = [Gift::class, Tag::class, GiftTag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}