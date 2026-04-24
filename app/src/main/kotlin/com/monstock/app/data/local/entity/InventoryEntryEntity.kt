package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

@Entity(
    tableName = "inventory_entries",
    foreignKeys = [
        ForeignKey(
            entity = InventorySessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sessionId")]
)
data class InventoryEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    /** Soft reference — kept even if the item is later deleted. */
    val itemId: Long,
    val itemName: String,
    val shelfId: Long,
    val shelfName: String,
    val unit: ItemUnit,
    val recordedQuantity: Double,
    val recordedExpiryDate: LocalDate? = null,
    val countedQuantity: Double? = null,
    val correctedExpiryDate: LocalDate? = null,
    val isChecked: Boolean = false,
    val scannedViaBarcode: Boolean = false
)
