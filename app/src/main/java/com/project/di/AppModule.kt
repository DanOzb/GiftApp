package com.project.di

import android.content.Context
import androidx.room.Room
import com.project.data.local.AppDatabase
import com.project.data.local.GiftDao
import com.project.data.repository.GiftRepositoryImpl
import com.project.domain.repository.GiftRepository
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
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "gift_database"
            ).build()
        }

        @Provides
        fun provideGiftDao(database: AppDatabase): GiftDao {
            return database.giftDao()
        }
    }
}