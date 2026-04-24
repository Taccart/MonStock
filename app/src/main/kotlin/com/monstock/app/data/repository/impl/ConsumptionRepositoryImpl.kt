package com.monstock.app.data.repository.impl

import com.monstock.app.data.local.dao.ConsumptionEventDao
import com.monstock.app.data.local.dao.ItemConsumptionSummary
import com.monstock.app.data.local.entity.ConsumptionEventEntity
import com.monstock.app.data.repository.ConsumptionRepository
import com.monstock.app.domain.model.ConsumptionEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class ConsumptionRepositoryImpl @Inject constructor(
    private val dao: ConsumptionEventDao
) : ConsumptionRepository {

    override suspend fun logEvent(event: ConsumptionEvent) =
        dao.insertEvent(event.toEntity())

    override fun getAllEvents(): Flow<List<ConsumptionEvent>> =
        dao.getAllEvents().map { list -> list.map { it.toDomain() } }

    override fun getEventsSince(fromEpochDay: Long): Flow<List<ConsumptionEvent>> =
        dao.getEventsSince(fromEpochDay).map { list -> list.map { it.toDomain() } }

    override fun getMostConsumedItems(): Flow<List<ItemConsumptionSummary>> =
        dao.getMostConsumedItems()

    override fun getMonthlySpend(year: Int, month: Int): Flow<Double?> {
        val ym = YearMonth.of(year, month)
        val from = ym.atDay(1).toEpochDay()
        val to = ym.atEndOfMonth().plusDays(1).toEpochDay()
        return dao.getMonthlySpend(from, to)
    }

    override fun getTotalConsumedCount(): Flow<Int> = dao.getTotalConsumedCount()

    override fun getTotalWastedCount(): Flow<Int> = dao.getTotalWastedCount()

    // --- Mappers ---

    private fun ConsumptionEventEntity.toDomain() = ConsumptionEvent(
        id = id,
        itemId = itemId,
        itemName = itemName,
        quantity = quantity,
        unit = unit,
        category = category,
        eventType = eventType,
        date = date,
        purchasePricePerUnit = purchasePricePerUnit
    )

    private fun ConsumptionEvent.toEntity() = ConsumptionEventEntity(
        id = id,
        itemId = itemId,
        itemName = itemName,
        quantity = quantity,
        unit = unit,
        category = category,
        eventType = eventType,
        date = date,
        purchasePricePerUnit = purchasePricePerUnit
    )
}
