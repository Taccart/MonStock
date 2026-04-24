package com.monstock.app.data.repository.impl

import com.monstock.app.data.local.dao.InventoryEntryDao
import com.monstock.app.data.local.dao.InventorySessionDao
import com.monstock.app.data.local.dao.ItemDao
import com.monstock.app.data.local.dao.ShelfDao
import com.monstock.app.data.local.entity.InventoryEntryEntity
import com.monstock.app.data.local.entity.InventorySessionEntity
import com.monstock.app.data.model.InventorySessionStatus
import com.monstock.app.data.repository.InventoryRepository
import com.monstock.app.domain.model.InventoryDiscrepancy
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.domain.model.InventorySession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val sessionDao: InventorySessionDao,
    private val entryDao: InventoryEntryDao,
    private val shelfDao: ShelfDao,
    private val itemDao: ItemDao
) : InventoryRepository {

    // ── Sessions ─────────────────────────────────────────────────────────────

    override fun getAllSessions(): Flow<List<InventorySession>> =
        sessionDao.getAllSessions().map { list -> list.map { it.toDomain() } }

    override fun getSessionsForPantry(pantryId: Long): Flow<List<InventorySession>> =
        sessionDao.getSessionsForPantry(pantryId).map { list -> list.map { it.toDomain() } }

    override suspend fun getActiveSession(): InventorySession? =
        sessionDao.getActiveSession()?.toDomain()

    override suspend fun getSessionById(id: Long): InventorySession? =
        sessionDao.getSessionById(id)?.toDomain()

    override suspend fun startSession(
        pantryId: Long,
        pantryName: String,
        shelfId: Long?,
        shelfName: String?,
        notes: String?
    ): Long {
        // Gather items in scope (either one shelf or all shelves of the pantry)
        val items = if (shelfId != null) {
            itemDao.getItemsByShelf(shelfId).first()
        } else {
            val shelves = shelfDao.getShelvesByPantry(pantryId).first()
            shelves.flatMap { shelf ->
                itemDao.getItemsByShelf(shelf.id).first().map { it to shelf }
            }.map { it.first }
            // Keep shelf info — redo with full pair
            shelves.flatMap { shelf ->
                itemDao.getItemsByShelf(shelf.id).first()
            }
        }

        // For the shelf-name lookup we need the shelf entity
        val shelfLookup: Map<Long, String> = if (shelfId != null) {
            val shelf = shelfDao.getShelfById(shelfId)
            if (shelf != null) mapOf(shelf.id to shelf.name) else emptyMap()
        } else {
            shelfDao.getShelvesByPantry(pantryId).first().associate { it.id to it.name }
        }

        val session = InventorySessionEntity(
            pantryId = pantryId,
            pantryName = pantryName,
            shelfId = shelfId,
            shelfName = shelfName,
            status = InventorySessionStatus.IN_PROGRESS,
            startedAt = LocalDateTime.now(),
            notes = notes,
            totalItemsInScope = items.size
        )
        val sessionId = sessionDao.insertSession(session)

        // Create one entry per item
        val entries = items.map { item ->
            InventoryEntryEntity(
                sessionId = sessionId,
                itemId = item.id,
                itemName = item.name,
                shelfId = item.shelfId,
                shelfName = shelfLookup[item.shelfId] ?: "",
                unit = item.unit,
                recordedQuantity = item.quantity,
                recordedExpiryDate = item.expiryDate
            )
        }
        entryDao.insertEntries(entries)
        return sessionId
    }

    override suspend fun cancelSession(sessionId: Long) {
        val entity = sessionDao.getSessionById(sessionId) ?: return
        sessionDao.updateSession(
            entity.copy(
                status = InventorySessionStatus.CANCELLED,
                completedAt = LocalDateTime.now()
            )
        )
    }

    override suspend fun completeAndApply(
        sessionId: Long,
        discrepancies: List<InventoryDiscrepancy>
    ) {
        // Apply selected discrepancies to live item records
        discrepancies.filter { it.isSelected }.forEach { discrepancy ->
            val item = itemDao.getItemById(discrepancy.itemId) ?: return@forEach
            val newQty = discrepancy.countedQuantity ?: item.quantity
            val newExpiry = discrepancy.correctedExpiryDate ?: item.expiryDate
            itemDao.updateItem(item.copy(quantity = newQty, expiryDate = newExpiry))
        }

        // Mark session as COMPLETED
        val entity = sessionDao.getSessionById(sessionId) ?: return
        sessionDao.updateSession(
            entity.copy(
                status = InventorySessionStatus.COMPLETED,
                completedAt = LocalDateTime.now()
            )
        )
    }

    // ── Entries ──────────────────────────────────────────────────────────────

    override fun getEntriesForSession(sessionId: Long): Flow<List<InventoryEntry>> =
        entryDao.getEntriesForSession(sessionId).map { list -> list.map { it.toDomain() } }

    override suspend fun checkEntry(
        entryId: Long,
        countedQuantity: Double?,
        correctedExpiryDate: LocalDate?,
        scannedViaBarcode: Boolean
    ) {
        entryDao.checkEntry(
            entryId = entryId,
            countedQty = countedQuantity,
            correctedExpiry = correctedExpiryDate?.toEpochDay(),
            scanned = scannedViaBarcode
        )
    }

    override suspend fun findEntryByBarcode(sessionId: Long, barcode: String): InventoryEntry? {
        val item = itemDao.getItemByBarcode(barcode) ?: return null
        return entryDao.getEntryByItem(sessionId, item.id)?.toDomain()
    }

    override suspend fun refreshSessionProgress(sessionId: Long) {
        val checked = entryDao.countCheckedEntries(sessionId)
        val discrepancies = entryDao.countDiscrepancies(sessionId)
        sessionDao.updateProgress(sessionId, checked, discrepancies)
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private fun InventorySessionEntity.toDomain() = InventorySession(
        id = id,
        pantryId = pantryId,
        pantryName = pantryName,
        shelfId = shelfId,
        shelfName = shelfName,
        status = status,
        startedAt = startedAt,
        completedAt = completedAt,
        notes = notes,
        totalItemsInScope = totalItemsInScope,
        checkedItemCount = checkedItemCount,
        discrepancyCount = discrepancyCount
    )

    private fun InventoryEntryEntity.toDomain() = InventoryEntry(
        id = id,
        sessionId = sessionId,
        itemId = itemId,
        itemName = itemName,
        shelfId = shelfId,
        shelfName = shelfName,
        unit = unit,
        recordedQuantity = recordedQuantity,
        recordedExpiryDate = recordedExpiryDate,
        countedQuantity = countedQuantity,
        correctedExpiryDate = correctedExpiryDate,
        isChecked = isChecked,
        scannedViaBarcode = scannedViaBarcode
    )
}
