package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monstock.app.data.local.entity.PantryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryDao {

    @Query("SELECT * FROM pantries ORDER BY name ASC")
    fun getAllPantries(): Flow<List<PantryEntity>>

    @Query("SELECT * FROM pantries WHERE id = :id")
    suspend fun getPantryById(id: Long): PantryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPantry(pantry: PantryEntity): Long

    @Update
    suspend fun updatePantry(pantry: PantryEntity)

    @Delete
    suspend fun deletePantry(pantry: PantryEntity)

    @Query("DELETE FROM pantries WHERE id = :id")
    suspend fun deletePantryById(id: Long)
}
