package com.monstock.app.di

import android.content.Context
import androidx.room.Room
import com.monstock.app.data.local.dao.ItemDao
import com.monstock.app.data.local.dao.PantryDao
import com.monstock.app.data.local.dao.ShelfDao
import com.monstock.app.data.local.database.MonStockDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MonStockDatabase {
        return Room.databaseBuilder(
            context,
            MonStockDatabase::class.java,
            MonStockDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true) // Replace with proper migrations before production
            .build()
    }

    @Provides
    @Singleton
    fun providePantryDao(db: MonStockDatabase): PantryDao = db.pantryDao()

    @Provides
    @Singleton
    fun provideShelfDao(db: MonStockDatabase): ShelfDao = db.shelfDao()

    @Provides
    @Singleton
    fun provideItemDao(db: MonStockDatabase): ItemDao = db.itemDao()
}
