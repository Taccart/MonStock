package com.monstock.app.data.repository

import com.monstock.app.data.model.ItemCategory
import com.monstock.app.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllItems(): Flow<List<Item>>
    fun getItemsByShelf(shelfId: Long): Flow<List<Item>>
    fun getItemsExpiringSoon(withinDays: Long = 7): Flow<List<Item>>
    fun getExpiredItems(): Flow<List<Item>>
    fun getLowStockItems(): Flow<List<Item>>
    fun searchItems(query: String): Flow<List<Item>>
    fun getItemsByCategory(category: ItemCategory): Flow<List<Item>>
    fun getTotalItemCount(): Flow<Int>
    suspend fun getItemById(id: Long): Item?
    suspend fun getItemByBarcode(barcode: String): Item?
    suspend fun insertItem(item: Item): Long
    suspend fun updateItem(item: Item)
    suspend fun deleteItem(id: Long)
}
