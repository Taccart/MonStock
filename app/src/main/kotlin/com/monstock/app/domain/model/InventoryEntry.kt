package com.monstock.app.domain.model

import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

/**
 * Represents one item checked during an [InventorySession].
 */
data class InventoryEntry(
    val id: Long = 0,
    val sessionId: Long,
    val itemId: Long,
    val itemName: String,
    val shelfId: Long,
    val shelfName: String,
    val unit: ItemUnit,
    /** Quantity recorded in the database before the session. */
    val recordedQuantity: Double,
    /** Expiry date recorded in the database before the session. */
    val recordedExpiryDate: LocalDate?,
    /** Quantity the user actually counted; null = not yet checked. */
    val countedQuantity: Double? = null,
    /** Corrected expiry date entered by the user; null = not changed. */
    val correctedExpiryDate: LocalDate? = null,
    /** True when the user has explicitly confirmed this entry (even with no changes). */
    val isChecked: Boolean = false,
    /** True if the item was identified via barcode scan during this session. */
    val scannedViaBarcode: Boolean = false
) {
    val hasQuantityDiscrepancy: Boolean
        get() = countedQuantity != null && countedQuantity != recordedQuantity

    val hasExpiryDiscrepancy: Boolean
        get() = correctedExpiryDate != null && correctedExpiryDate != recordedExpiryDate

    val hasAnyDiscrepancy: Boolean
        get() = hasQuantityDiscrepancy || hasExpiryDiscrepancy
}
