package com.monstock.app.data.repository

import com.monstock.app.domain.model.Pantry
import kotlinx.coroutines.flow.Flow

interface PantryRepository {
    fun getAllPantries(): Flow<List<Pantry>>
    suspend fun getPantryById(id: Long): Pantry?
    suspend fun insertPantry(pantry: Pantry): Long
    suspend fun updatePantry(pantry: Pantry)
    suspend fun deletePantry(id: Long)
}
