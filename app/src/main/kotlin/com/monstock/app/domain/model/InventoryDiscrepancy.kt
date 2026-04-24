package com.monstock.app.domain.model

import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

/**
 * A convenience projection used by the discrepancy report screen.
 * Contains only entries that differ from the recorded values or that
 * were never checked (possibly missing).
 */
data class InventoryDiscrepancy(
    val entryId: Long,
    val itemId: Long,
    val itemName: String,
    val shelfName: String,
    val unit: ItemUnit,
    val recordedQuantity: Double,
    val countedQuantity: Double?,
    val recordedExpiryDate: LocalDate?,
    val correctedExpiryDate: LocalDate?,
    /** True when the user never scanned / checked this item. */
    val isPossiblyMissing: Boolean,
    /** Whether this discrepancy is selected for bulk application. */
    val isSelected: Boolean = true
)
