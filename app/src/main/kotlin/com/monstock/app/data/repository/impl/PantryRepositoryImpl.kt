package com.monstock.app.data.repository.impl

import com.monstock.app.data.local.dao.PantryDao
import com.monstock.app.data.local.entity.PantryEntity
import com.monstock.app.data.repository.PantryRepository
import com.monstock.app.domain.model.Pantry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PantryRepositoryImpl @Inject constructor(
    private val pantryDao: PantryDao
) : PantryRepository {

    override fun getAllPantries(): Flow<List<Pantry>> =
        pantryDao.getAllPantries().map { list -> list.map { it.toDomain() } }

    override suspend fun getPantryById(id: Long): Pantry? =
        pantryDao.getPantryById(id)?.toDomain()

    override suspend fun insertPantry(pantry: Pantry): Long =
        pantryDao.insertPantry(pantry.toEntity())

    override suspend fun updatePantry(pantry: Pantry) =
        pantryDao.updatePantry(pantry.toEntity())

    override suspend fun deletePantry(id: Long) =
        pantryDao.deletePantryById(id)

    // --- Mappers ---

    private fun PantryEntity.toDomain() = Pantry(id = id, name = name)

    private fun Pantry.toEntity() = PantryEntity(id = id, name = name)
}
