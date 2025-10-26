package com.example.giftapp.di

import android.content.Context
import androidx.room.Room
import com.example.giftapp.data.local.GiftDao
import com.example.giftapp.data.local.GiftDatabase
import com.example.giftapp.data.repository.GiftRepositoryImpl
import com.example.giftapp.domain.repository.GiftRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindGiftRepository(
        giftRepositoryImpl: GiftRepositoryImpl
    ): GiftRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): GiftDatabase {
            return Room.databaseBuilder(
                context,
                GiftDatabase::class.java,
                "gift_database"
            ).build()
        }

        @Provides
        fun provideGiftDao(database: GiftDatabase): GiftDao {
            return database.giftDao()
        }
    }
}