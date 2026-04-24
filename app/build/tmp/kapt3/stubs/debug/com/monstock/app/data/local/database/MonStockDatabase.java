package com.monstock.app.data.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.monstock.app.data.local.dao.ItemDao;
import com.monstock.app.data.local.dao.PantryDao;
import com.monstock.app.data.local.dao.ShelfDao;
import com.monstock.app.data.local.entity.ItemEntity;
import com.monstock.app.data.local.entity.PantryEntity;
import com.monstock.app.data.local.entity.ShelfEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/monstock/app/data/local/database/MonStockDatabase;", "Landroidx/room/RoomDatabase;", "()V", "itemDao", "Lcom/monstock/app/data/local/dao/ItemDao;", "pantryDao", "Lcom/monstock/app/data/local/dao/PantryDao;", "shelfDao", "Lcom/monstock/app/data/local/dao/ShelfDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.monstock.app.data.local.entity.PantryEntity.class, com.monstock.app.data.local.entity.ShelfEntity.class, com.monstock.app.data.local.entity.ItemEntity.class}, version = 2, exportSchema = true)
@androidx.room.TypeConverters(value = {com.monstock.app.data.local.database.Converters.class})
public abstract class MonStockDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "monstock_database";
    @org.jetbrains.annotations.NotNull()
    public static final com.monstock.app.data.local.database.MonStockDatabase.Companion Companion = null;
    
    public MonStockDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.monstock.app.data.local.dao.PantryDao pantryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.monstock.app.data.local.dao.ShelfDao shelfDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.monstock.app.data.local.dao.ItemDao itemDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/monstock/app/data/local/database/MonStockDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}