package com.project.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GiftDao{
    @Query("SELECT * FROM gifts WHERE gifts.type='LETTER'")
    fun getAllLetters() : Flow<List<Gift>>

    @Query("SELECT * FROM gifts WHERE gifts.type='PICTURE'")
    fun getAllPictures() : Flow<List<Gift>>

    @Query("SELECT * FROM gifts WHERE gifts.type='VIDEO'")
    fun getAllVideos() : Flow<List<Gift>>

    @Query("SELECT * FROM gifts WHERE gifts.type='LETTER' AND gifts.favorite=true")
    fun getFavoriteLetters() : Flow<List<Gift>>

    @Query("SELECT * FROM gifts WHERE gifts.type='PICTURE' AND gifts.favorite=true")
    fun getFavoritePictures() : Flow<List<Gift>>

    @Query("SELECT * FROM gifts WHERE gifts.type='VIDEO' AND gifts.favorite=true")
    fun getFavoriteVideos() : Flow<List<Gift>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg gifts: Gift)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gift: Gift)

    @Update()
    suspend fun updateGift(gift: Gift)

    @Delete
    suspend fun delete(gift: Gift)
}

@Database(entities = [Gift::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun giftDao() : GiftDao
}
