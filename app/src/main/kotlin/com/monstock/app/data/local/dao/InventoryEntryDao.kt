package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monstock.app.data.local.entity.InventoryEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryEntryDao {

    @Query("SELECT * FROM inventory_entries WHERE sessionId = :sessionId ORDER BY shelfName ASC, itemName ASC")
    fun getEntriesForSession(sessionId: Long): Flow<List<InventoryEntryEntity>>

    @Query("SELECT * FROM inventory_entries WHERE sessionId = :sessionId AND itemId = :itemId LIMIT 1")
    suspend fun getEntryByItem(sessionId: Long, itemId: Long): InventoryEntryEntity?

    @Query("SELECT * FROM inventory_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): InventoryEntryEntity?

    @Query("SELECT COUNT(*) FROM inventory_entries WHERE sessionId = :sessionId")
    suspend fun countEntries(sessionId: Long): Int

    @Query("SELECT COUNT(*) FROM inventory_entries WHERE sessionId = :sessionId AND isChecked = 1")
    suspend fun countCheckedEntries(sessionId: Long): Int

    @Query("""
        SELECT COUNT(*) FROM inventory_entries
        WHERE sessionId = :sessionId
          AND isChecked = 1
          AND (
            (countedQuantity IS NOT NULL AND countedQuantity != recordedQuantity)
            OR
            (correctedExpiryDate IS NOT NULL AND correctedExpiryDate != recordedExpiryDate)
          )
    """)
    suspend fun countDiscrepancies(sessionId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntries(entries: List<InventoryEntryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: InventoryEntryEntity): Long

    @Update
    suspend fun updateEntry(entry: InventoryEntryEntity)

    @Query("""
        UPDATE inventory_entries
        SET countedQuantity = :countedQty,
            correctedExpiryDate = :correctedExpiry,
            isChecked = 1,
            scannedViaBarcode = :scanned
        WHERE id = :entryId
    """)
    suspend fun checkEntry(
        entryId: Long,
        countedQty: Double?,
        correctedExpiry: Long?,
        scanned: Boolean
    )

    @Query("DELETE FROM inventory_entries WHERE sessionId = :sessionId")
    suspend fun deleteEntriesForSession(sessionId: Long)
}
