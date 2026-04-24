package com.monstock.app.data.local.database

import androidx.room.TypeConverter
import com.monstock.app.data.model.ConsumptionEventType
import com.monstock.app.data.model.InventorySessionStatus
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {

    // --- LocalDate ---

    @TypeConverter
    fun fromLocalDate(value: Long?): LocalDate? =
        value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun localDateToEpochDay(date: LocalDate?): Long? = date?.toEpochDay()

    // --- ItemCategory ---

    @TypeConverter
    fun fromItemCategory(value: String?): ItemCategory? =
        value?.let { enumValueOf<ItemCategory>(it) }

    @TypeConverter
    fun itemCategoryToString(category: ItemCategory?): String? = category?.name

    // --- ItemUnit ---

    @TypeConverter
    fun fromItemUnit(value: String?): ItemUnit? =
        value?.let { enumValueOf<ItemUnit>(it) }

    @TypeConverter
    fun itemUnitToString(unit: ItemUnit?): String? = unit?.name

    // --- ConsumptionEventType ---

    @TypeConverter
    fun fromConsumptionEventType(value: String?): ConsumptionEventType? =
        value?.let { enumValueOf<ConsumptionEventType>(it) }

    @TypeConverter
    fun consumptionEventTypeToString(type: ConsumptionEventType?): String? = type?.name

    // --- LocalDateTime ---

    @TypeConverter
    fun fromLocalDateTime(value: Long?): LocalDateTime? =
        value?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) }

    @TypeConverter
    fun localDateTimeToEpochSecond(dateTime: LocalDateTime?): Long? =
        dateTime?.toEpochSecond(java.time.ZoneOffset.UTC)

    // --- InventorySessionStatus ---

    @TypeConverter
    fun fromInventorySessionStatus(value: String?): InventorySessionStatus? =
        value?.let { enumValueOf<InventorySessionStatus>(it) }

    @TypeConverter
    fun inventorySessionStatusToString(status: InventorySessionStatus?): String? = status?.name
}
