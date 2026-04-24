package com.monstock.app.data.repository.impl

import com.monstock.app.data.local.dao.ItemDao
import com.monstock.app.data.local.entity.ItemEntity
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao
) : ItemRepository {

    override fun getAllItems(): Flow<List<Item>> =
        itemDao.getAllItems().map { list -> list.map { it.toDomain() } }

    override fun getItemsByShelf(shelfId: Long): Flow<List<Item>> =
        itemDao.getItemsByShelf(shelfId).map { list -> list.map { it.toDomain() } }

    override fun getItemsExpiringSoon(withinDays: Long): Flow<List<Item>> {
        val today = LocalDate.now()
        return itemDao.getItemsExpiringSoon(
            todayEpochDay = today.toEpochDay(),
            epochDayLimit = today.plusDays(withinDays).toEpochDay()
        ).map { list -> list.map { it.toDomain() } }
    }

    override fun getExpiredItems(): Flow<List<Item>> =
        itemDao.getExpiredItems(LocalDate.now().toEpochDay())
            .map { list -> list.map { it.toDomain() } }

    override fun getLowStockItems(): Flow<List<Item>> =
        itemDao.getLowStockItems().map { list -> list.map { it.toDomain() } }

    override fun searchItems(query: String): Flow<List<Item>> =
        itemDao.searchItems(query).map { list -> list.map { it.toDomain() } }

    override fun getItemsByCategory(category: ItemCategory): Flow<List<Item>> =
        itemDao.getItemsByCategory(category).map { list -> list.map { it.toDomain() } }

    override fun getTotalItemCount(): Flow<Int> = itemDao.getTotalItemCount()

    override suspend fun getItemById(id: Long): Item? =
        itemDao.getItemById(id)?.toDomain()

    override suspend fun getItemByBarcode(barcode: String): Item? =
        itemDao.getItemByBarcode(barcode)?.toDomain()

    override suspend fun insertItem(item: Item): Long =
        itemDao.insertItem(item.toEntity())

    override suspend fun updateItem(item: Item) =
        itemDao.updateItem(item.toEntity())

    override suspend fun deleteItem(id: Long) =
        itemDao.deleteItemById(id)

    // --- Mappers ---

    private fun ItemEntity.toDomain() = Item(
        id = id,
        shelfId = shelfId,
        name = name,
        brand = brand,
        category = category,
        quantity = quantity,
        unit = unit,
        purchaseDate = purchaseDate,
        expiryDate = expiryDate,
        minimumStockThreshold = minimumStockThreshold,
        barcode = barcode,
        photoUri = photoUri,
        notes = notes
    )

    private fun Item.toEntity() = ItemEntity(
        id = id,
        shelfId = shelfId,
        name = name,
        brand = brand,
        category = category,
        quantity = quantity,
        unit = unit,
        purchaseDate = purchaseDate,
        expiryDate = expiryDate,
        minimumStockThreshold = minimumStockThreshold,
        barcode = barcode,
        photoUri = photoUri,
        notes = notes
    )
}
