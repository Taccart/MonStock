package com.monstock.app.di

import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.PantryRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.data.repository.impl.ItemRepositoryImpl
import com.monstock.app.data.repository.impl.PantryRepositoryImpl
import com.monstock.app.data.repository.impl.ShelfRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPantryRepository(impl: PantryRepositoryImpl): PantryRepository

    @Binds
    @Singleton
    abstract fun bindShelfRepository(impl: ShelfRepositoryImpl): ShelfRepository

    @Binds
    @Singleton
    abstract fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository
}
