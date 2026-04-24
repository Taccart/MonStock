package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monstock.app.data.local.entity.BarcodeCacheEntity

@Dao
interface BarcodeCacheDao {

    @Query("SELECT * FROM barcode_cache WHERE barcode = :barcode LIMIT 1")
    suspend fun getByBarcode(barcode: String): BarcodeCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: BarcodeCacheEntity)

    @Query("DELETE FROM barcode_cache WHERE cachedAt < :threshold")
    suspend fun deleteOlderThan(threshold: Long)
}
