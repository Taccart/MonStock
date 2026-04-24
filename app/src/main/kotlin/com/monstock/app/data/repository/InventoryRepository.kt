package com.monstock.app.data.repository

import com.monstock.app.data.model.InventorySessionStatus
import com.monstock.app.domain.model.InventoryDiscrepancy
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.domain.model.InventorySession
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {

    /** All sessions, newest first. */
    fun getAllSessions(): Flow<List<InventorySession>>

    /** Sessions scoped to a specific pantry, newest first. */
    fun getSessionsForPantry(pantryId: Long): Flow<List<InventorySession>>

    /** The currently active (IN_PROGRESS) session, or null. */
    suspend fun getActiveSession(): InventorySession?

    /** Get a single session by id. */
    suspend fun getSessionById(id: Long): InventorySession?

    /**
     * Start a new inventory session for [pantryId] (and optionally scoped to [shelfId]).
     * Snapshots the current items in scope and creates one [InventoryEntry] per item.
     * Returns the new session id.
     */
    suspend fun startSession(
        pantryId: Long,
        pantryName: String,
        shelfId: Long? = null,
        shelfName: String? = null,
        notes: String? = null
    ): Long

    /** Cancel the session without applying any changes. */
    suspend fun cancelSession(sessionId: Long)

    /**
     * Complete the session: mark it COMPLETED and apply all selected discrepancies
     * to the live item records in a single transaction.
     */
    suspend fun completeAndApply(
        sessionId: Long,
        discrepancies: List<InventoryDiscrepancy>
    )

    // ── Entries ──────────────────────────────────────────────────────────────

    /** Live list of entries for a session. */
    fun getEntriesForSession(sessionId: Long): Flow<List<InventoryEntry>>

    /** Mark an entry as checked with the counted values. */
    suspend fun checkEntry(
        entryId: Long,
        countedQuantity: Double?,
        correctedExpiryDate: java.time.LocalDate?,
        scannedViaBarcode: Boolean = false
    )

    /**
     * Find the entry for a given barcode within the active session.
     * Returns null if the barcode is not found in scope.
     */
    suspend fun findEntryByBarcode(sessionId: Long, barcode: String): InventoryEntry?

    /** Recompute and persist the checked/discrepancy counters on the session row. */
    suspend fun refreshSessionProgress(sessionId: Long)
}
