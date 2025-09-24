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
    suspend fun getAllLetters() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='PICTURE'")
    suspend fun getAllPictures() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='VIDEO'")
    suspend fun getAllVideos() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='LETTER' AND gifts.favorite=true")
    suspend fun getFavoriteLetters() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='PICTURE' AND gifts.favorite=true")
    suspend fun getFavoritePictures() : List<Gift>

    @Query("SELECT * FROM gifts WHERE gifts.type='VIDEO' AND gifts.favorite=true")
    suspend fun getFavoriteVideos() : List<Gift>

    @Insert
    suspend fun insertAll(vararg gifts: Gift)

    @Insert
    suspend fun insert(gift: Gift)

    @Update
    suspend fun updateGift(gift: Gift)

    @Delete
    suspend fun delete(gift: Gift)
}

@Database(entities = [Gift::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}
