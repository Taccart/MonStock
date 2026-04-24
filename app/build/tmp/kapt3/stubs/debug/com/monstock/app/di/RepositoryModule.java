package com.monstock.app.di;

import com.monstock.app.data.repository.ItemRepository;
import com.monstock.app.data.repository.PantryRepository;
import com.monstock.app.data.repository.ShelfRepository;
import com.monstock.app.data.repository.impl.ItemRepositoryImpl;
import com.monstock.app.data.repository.impl.PantryRepositoryImpl;
import com.monstock.app.data.repository.impl.ShelfRepositoryImpl;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\fH\'\u00a8\u0006\r"}, d2 = {"Lcom/monstock/app/di/RepositoryModule;", "", "()V", "bindItemRepository", "Lcom/monstock/app/data/repository/ItemRepository;", "impl", "Lcom/monstock/app/data/repository/impl/ItemRepositoryImpl;", "bindPantryRepository", "Lcom/monstock/app/data/repository/PantryRepository;", "Lcom/monstock/app/data/repository/impl/PantryRepositoryImpl;", "bindShelfRepository", "Lcom/monstock/app/data/repository/ShelfRepository;", "Lcom/monstock/app/data/repository/impl/ShelfRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.monstock.app.data.repository.PantryRepository bindPantryRepository(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.impl.PantryRepositoryImpl impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.monstock.app.data.repository.ShelfRepository bindShelfRepository(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.impl.ShelfRepositoryImpl impl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.monstock.app.data.repository.ItemRepository bindItemRepository(@org.jetbrains.annotations.NotNull()
    com.monstock.app.data.repository.impl.ItemRepositoryImpl impl);
}