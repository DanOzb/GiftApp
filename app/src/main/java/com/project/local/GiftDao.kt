package com.project.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface GiftDao{
    @Query("SELECT * FROM letters")
    fun getAllLetters() : List<Letter>

    @Query("SELECT * FROM pictures")
    fun getAllPictures() : List<Picture>

    @Query("SELECT * FROM videos")
    fun getAllVideos() : List<Video>

    @Query("SELECT * FROM letters WHERE favorite=true")
    fun getFavoriteLetters() : List<Letter>

    @Query("SELECT * FROM pictures WHERE favorite=true")
    fun getFavoritePictures() : List<Picture>

    @Query("SELECT * FROM videos WHERE favorite=true")
    fun getFavoriteVideos() : List<Video>
}

@Database(entities = [Letter::class, Picture::class, Video::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}
