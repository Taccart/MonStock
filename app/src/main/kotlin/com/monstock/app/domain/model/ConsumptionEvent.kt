package com.monstock.app.domain.model

import com.monstock.app.data.model.ConsumptionEventType
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

data class ConsumptionEvent(
    val id: Long = 0,
    val itemId: Long,
    val itemName: String,
    val quantity: Double,
    val unit: ItemUnit,
    val category: ItemCategory,
    val eventType: ConsumptionEventType,
    val date: LocalDate,
    val purchasePricePerUnit: Double? = null
)
