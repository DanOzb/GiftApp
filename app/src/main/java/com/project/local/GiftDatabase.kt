package com.project.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.GiftType
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

