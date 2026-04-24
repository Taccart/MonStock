package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.monstock.app.data.model.ConsumptionEventType
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

@Entity(tableName = "consumption_events")
data class ConsumptionEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    /** Soft reference — kept even after item is deleted so history is preserved. */
    val itemId: Long,
    val itemName: String,
    val quantity: Double,
    val unit: ItemUnit,
    val category: ItemCategory,
    val eventType: ConsumptionEventType,
    val date: LocalDate,
    /** Optional price per unit at time of purchase, used for spend calculations. */
    val purchasePricePerUnit: Double? = null
)
