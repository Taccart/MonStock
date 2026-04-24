package com.monstock.app.data.repository

import com.monstock.app.domain.model.Shelf
import kotlinx.coroutines.flow.Flow

interface ShelfRepository {
    fun getShelvesByPantry(pantryId: Long): Flow<List<Shelf>>
    suspend fun getShelfById(id: Long): Shelf?
    suspend fun insertShelf(shelf: Shelf): Long
    suspend fun updateShelf(shelf: Shelf)
    suspend fun deleteShelf(id: Long)
}
