package com.monstock.app.data.repository.impl

import com.monstock.app.data.local.dao.ShelfDao
import com.monstock.app.data.local.entity.ShelfEntity
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.Shelf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShelfRepositoryImpl @Inject constructor(
    private val shelfDao: ShelfDao
) : ShelfRepository {

    override fun getShelvesByPantry(pantryId: Long): Flow<List<Shelf>> =
        shelfDao.getShelvesByPantry(pantryId).map { list -> list.map { it.toDomain() } }

    override suspend fun getShelfById(id: Long): Shelf? =
        shelfDao.getShelfById(id)?.toDomain()

    override suspend fun insertShelf(shelf: Shelf): Long =
        shelfDao.insertShelf(shelf.toEntity())

    override suspend fun updateShelf(shelf: Shelf) =
        shelfDao.updateShelf(shelf.toEntity())

    override suspend fun deleteShelf(id: Long) =
        shelfDao.deleteShelfById(id)

    // --- Mappers ---

    private fun ShelfEntity.toDomain() = Shelf(
        id = id,
        pantryId = pantryId,
        name = name,
        capacity = capacity
    )

    private fun Shelf.toEntity() = ShelfEntity(
        id = id,
        pantryId = pantryId,
        name = name,
        capacity = capacity
    )
}
