package com.monstock.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.monstock.app.data.local.dao.BarcodeCacheDao
import com.monstock.app.data.local.dao.ConsumptionEventDao
import com.monstock.app.data.local.dao.InventoryEntryDao
import com.monstock.app.data.local.dao.InventorySessionDao
import com.monstock.app.data.local.dao.ItemDao
import com.monstock.app.data.local.dao.PantryDao
import com.monstock.app.data.local.dao.ShelfDao
import com.monstock.app.data.local.entity.BarcodeCacheEntity
import com.monstock.app.data.local.entity.ConsumptionEventEntity
import com.monstock.app.data.local.entity.InventoryEntryEntity
import com.monstock.app.data.local.entity.InventorySessionEntity
import com.monstock.app.data.local.entity.ItemEntity
import com.monstock.app.data.local.entity.PantryEntity
import com.monstock.app.data.local.entity.ShelfEntity

@Database(
    entities = [
        PantryEntity::class,
        ShelfEntity::class,
        ItemEntity::class,
        BarcodeCacheEntity::class,
        ConsumptionEventEntity::class,
        InventorySessionEntity::class,
        InventoryEntryEntity::class
    ],
    version = 5,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MonStockDatabase : RoomDatabase() {

    abstract fun pantryDao(): PantryDao
    abstract fun shelfDao(): ShelfDao
    abstract fun itemDao(): ItemDao
    abstract fun barcodeCacheDao(): BarcodeCacheDao
    abstract fun consumptionEventDao(): ConsumptionEventDao
    abstract fun inventorySessionDao(): InventorySessionDao
    abstract fun inventoryEntryDao(): InventoryEntryDao

    companion object {
        const val DATABASE_NAME = "monstock_database"
    }
}
