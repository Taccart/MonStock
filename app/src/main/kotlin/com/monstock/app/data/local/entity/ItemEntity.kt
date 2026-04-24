package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = ShelfEntity::class,
            parentColumns = ["id"],
            childColumns = ["shelfId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("shelfId")]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
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
    val purchasePricePerUnit: Double? = null
)
