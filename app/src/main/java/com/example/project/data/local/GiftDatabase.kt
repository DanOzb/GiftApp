package com.example.project.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * Local database for opened gifts. Will be shown in grid layout.
 *
 */

@Entity(tableName = "letters")
data class Letter(
    @PrimaryKey val id: Int,
    val message: String,
    val favorite: Boolean
)

@Entity(tableName = "pictures")
data class Picture(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "file_path") val filePath: String,
    @ColumnInfo(name = "file_name")val fileName: String,
    val favorite: Boolean,
)

@Entity(tableName = "videos")
data class Video(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "file_path") val filePath: String,
    @ColumnInfo(name = "file_name")val fileName: String,
    val favorite: Boolean,
)

