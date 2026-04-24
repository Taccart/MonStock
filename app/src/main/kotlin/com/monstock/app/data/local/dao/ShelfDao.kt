package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monstock.app.data.local.entity.ShelfEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelfDao {

    @Query("SELECT * FROM shelves WHERE pantryId = :pantryId ORDER BY name ASC")
    fun getShelvesByPantry(pantryId: Long): Flow<List<ShelfEntity>>

    @Query("SELECT * FROM shelves WHERE id = :id")
    suspend fun getShelfById(id: Long): ShelfEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShelf(shelf: ShelfEntity): Long

    @Update
    suspend fun updateShelf(shelf: ShelfEntity)

    @Delete
    suspend fun deleteShelf(shelf: ShelfEntity)

    @Query("DELETE FROM shelves WHERE id = :id")
    suspend fun deleteShelfById(id: Long)
}
