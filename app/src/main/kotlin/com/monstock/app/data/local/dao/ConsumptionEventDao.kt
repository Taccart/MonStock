package com.monstock.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monstock.app.data.local.entity.ConsumptionEventEntity
import kotlinx.coroutines.flow.Flow

/** Summary projection used for "most consumed" aggregation queries. */
data class ItemConsumptionSummary(
    val itemName: String,
    val totalQty: Double
)

@Dao
interface ConsumptionEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: ConsumptionEventEntity)

    @Query("SELECT * FROM consumption_events ORDER BY date DESC")
    fun getAllEvents(): Flow<List<ConsumptionEventEntity>>

    @Query("SELECT * FROM consumption_events WHERE date >= :fromEpochDay ORDER BY date DESC")
    fun getEventsSince(fromEpochDay: Long): Flow<List<ConsumptionEventEntity>>

    @Query("""
        SELECT itemName, SUM(quantity) AS totalQty
        FROM consumption_events
        WHERE eventType = 'CONSUMED'
        GROUP BY itemName
        ORDER BY totalQty DESC
        LIMIT 10
    """)
    fun getMostConsumedItems(): Flow<List<ItemConsumptionSummary>>

    @Query("""
        SELECT SUM(quantity * purchasePricePerUnit)
        FROM consumption_events
        WHERE purchasePricePerUnit IS NOT NULL
          AND date >= :fromEpochDay
          AND date < :toEpochDay
    """)
    fun getMonthlySpend(fromEpochDay: Long, toEpochDay: Long): Flow<Double?>

    @Query("SELECT COUNT(*) FROM consumption_events WHERE eventType = 'CONSUMED'")
    fun getTotalConsumedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM consumption_events WHERE eventType = 'WASTED'")
    fun getTotalWastedCount(): Flow<Int>
}
