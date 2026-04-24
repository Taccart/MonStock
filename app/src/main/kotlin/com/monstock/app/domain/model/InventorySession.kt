package com.monstock.app.domain.model

import com.monstock.app.data.model.InventorySessionStatus
import java.time.LocalDateTime

/**
 * Represents one inventory-taking session scoped to a pantry or a shelf.
 *
 * @param pantryId  the pantry being inventoried; null means whole-database scope (not exposed in UI)
 * @param shelfId   when non-null, the session is restricted to a single shelf
 */
data class InventorySession(
    val id: Long = 0,
    val pantryId: Long,
    val pantryName: String,
    val shelfId: Long? = null,
    val shelfName: String? = null,
    val status: InventorySessionStatus = InventorySessionStatus.IN_PROGRESS,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime? = null,
    val notes: String? = null,
    /** Total items in scope at session start. */
    val totalItemsInScope: Int = 0,
    /** Number of entries that have been checked so far. */
    val checkedItemCount: Int = 0,
    /** Number of entries that differ from the recorded values. */
    val discrepancyCount: Int = 0
)
