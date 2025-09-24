package com.project.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update

@Dao
interface GiftDao{
    @Query("SELECT * FROM gifts WHERE gifts.type='LETTER'")
    fun getAllLetters() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='PICTURE'")
    fun getAllPictures() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='VIDEO'")
    fun getAllVideos() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='LETTER' AND gifts.favorite=true")
    fun getFavoriteLetters() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='PICTURE' AND gifts.favorite=true")
    fun getFavoritePictures() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='VIDEO' AND gifts.favorite=true")
    fun getFavoriteVideos() : List<Gift>

    @Insert
    fun insertAll(vararg gifts: Gift)

    @Insert
    fun insert(gift: Gift)

    @Update
    fun updateGift(gift: Gift)

    @Delete
    fun delete(gift: Gift)
}

@Database(entities = [Gift::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}
