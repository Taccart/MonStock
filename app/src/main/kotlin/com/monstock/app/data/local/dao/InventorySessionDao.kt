package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monstock.app.data.local.entity.InventorySessionEntity
import com.monstock.app.data.model.InventorySessionStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface InventorySessionDao {

    @Query("SELECT * FROM inventory_sessions ORDER BY startedAt DESC")
    fun getAllSessions(): Flow<List<InventorySessionEntity>>

    @Query("SELECT * FROM inventory_sessions WHERE pantryId = :pantryId ORDER BY startedAt DESC")
    fun getSessionsForPantry(pantryId: Long): Flow<List<InventorySessionEntity>>

    @Query("SELECT * FROM inventory_sessions WHERE status = 'IN_PROGRESS' LIMIT 1")
    suspend fun getActiveSession(): InventorySessionEntity?

    @Query("SELECT * FROM inventory_sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): InventorySessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: InventorySessionEntity): Long

    @Update
    suspend fun updateSession(session: InventorySessionEntity)

    @Query("""
        UPDATE inventory_sessions
        SET status = :status, completedAt = :completedAt
        WHERE id = :id
    """)
    suspend fun completeSession(id: Long, status: String, completedAt: Long)

    @Query("""
        UPDATE inventory_sessions
        SET checkedItemCount = :checked, discrepancyCount = :discrepancies
        WHERE id = :id
    """)
    suspend fun updateProgress(id: Long, checked: Int, discrepancies: Int)

    @Query("DELETE FROM inventory_sessions WHERE id = :id")
    suspend fun deleteSession(id: Long)
}
