package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monstock.app.data.local.entity.ItemEntity
import com.monstock.app.data.model.ItemCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    /** All items on a specific shelf, sorted soonest-to-expire first. */
    @Query("SELECT * FROM items WHERE shelfId = :shelfId ORDER BY expiryDate ASC")
    fun getItemsByShelf(shelfId: Long): Flow<List<ItemEntity>>

    /** All items across all shelves, sorted soonest-to-expire first. */
    @Query("SELECT * FROM items ORDER BY expiryDate ASC")
    fun getAllItems(): Flow<List<ItemEntity>>

    /** Items expiring within the next [days] days (from today). */
    @Query("""
        SELECT * FROM items
        WHERE expiryDate IS NOT NULL
          AND expiryDate <= :epochDayLimit
          AND expiryDate >= :todayEpochDay
        ORDER BY expiryDate ASC
    """)
    fun getItemsExpiringSoon(todayEpochDay: Long, epochDayLimit: Long): Flow<List<ItemEntity>>

    /** Items that are already expired. */
    @Query("""
        SELECT * FROM items
        WHERE expiryDate IS NOT NULL
          AND expiryDate < :todayEpochDay
        ORDER BY expiryDate ASC
    """)
    fun getExpiredItems(todayEpochDay: Long): Flow<List<ItemEntity>>

    /** Items whose quantity is at or below their minimum stock threshold. */
    @Query("""
        SELECT * FROM items
        WHERE minimumStockThreshold IS NOT NULL
          AND quantity <= minimumStockThreshold
        ORDER BY name ASC
    """)
    fun getLowStockItems(): Flow<List<ItemEntity>>

    /** Full-text search on name and brand. */
    @Query("""
        SELECT * FROM items
        WHERE name LIKE '%' || :query || '%'
           OR brand LIKE '%' || :query || '%'
        ORDER BY expiryDate ASC
    """)
    fun searchItems(query: String): Flow<List<ItemEntity>>

    /** Filter by category. */
    @Query("SELECT * FROM items WHERE category = :category ORDER BY expiryDate ASC")
    fun getItemsByCategory(category: ItemCategory): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE barcode = :barcode LIMIT 1")
    suspend fun getItemByBarcode(barcode: String): ItemEntity?

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Long): ItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity): Long

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteItemById(id: Long)

    /** Count of all items (for dashboard). */
    @Query("SELECT COUNT(*) FROM items")
    fun getTotalItemCount(): Flow<Int>
}
