package com.monstock.app.data.repository

import com.monstock.app.data.local.dao.ItemConsumptionSummary
import com.monstock.app.domain.model.ConsumptionEvent
import kotlinx.coroutines.flow.Flow

interface ConsumptionRepository {

    suspend fun logEvent(event: ConsumptionEvent)

    fun getAllEvents(): Flow<List<ConsumptionEvent>>

    /** Events since the given epoch day. */
    fun getEventsSince(fromEpochDay: Long): Flow<List<ConsumptionEvent>>

    /** Top 10 most-consumed items by total quantity. */
    fun getMostConsumedItems(): Flow<List<ItemConsumptionSummary>>

    /** Total spend (sum of quantity × price) for a calendar month. */
    fun getMonthlySpend(year: Int, month: Int): Flow<Double?>

    fun getTotalConsumedCount(): Flow<Int>

    fun getTotalWastedCount(): Flow<Int>
}
