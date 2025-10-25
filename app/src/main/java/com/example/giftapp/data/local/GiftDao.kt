package com.example.giftapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.giftapp.domain.model.GiftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GiftDao{
    @Query("SELECT * FROM gifts")
    fun getAllGifts() : Flow<List<GiftEntity>>

    @Query("SELECT * FROM gifts WHERE gifts.favorite=true")
    fun getFavoriteGifts() : Flow<List<GiftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(giftEntity: GiftEntity)

    @Update()
    suspend fun updateGift(giftEntity: GiftEntity)

    @Delete
    suspend fun delete(giftEntity: GiftEntity)
}