package com.monstock.app.domain.model

import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

data class Item(
    val id: Long = 0,
    val shelfId: Long,
    val name: String,
    val brand: String? = null,
    val category: ItemCategory = ItemCategory.OTHER,
    val quantity: Double = 1.0,
    val unit: ItemUnit = ItemUnit.PIECE,
    val purchaseDate: LocalDate? = null,
    val expiryDate: LocalDate? = null,
    val minimumStockThreshold: Double? = null,
    val barcode: String? = null,
    val photoUri: String? = null,
    val notes: String? = null,
    /** Optional purchase price per unit (€), used for spend tracking. */
    val purchasePricePerUnit: Double? = null
) {
    /** True if expiry date is in the past. */
    val isExpired: Boolean
        get() = expiryDate != null && expiryDate.isBefore(LocalDate.now())

    /** True if expiry date is within the next [withinDays] days. */
    fun isExpiringSoon(withinDays: Long = 7): Boolean {
        if (expiryDate == null) return false
        val today = LocalDate.now()
        return !isExpired && !expiryDate.isAfter(today.plusDays(withinDays))
    }

    /** True if quantity is at or below the minimum stock threshold. */
    val isLowStock: Boolean
        get() = minimumStockThreshold != null && quantity <= minimumStockThreshold
}
