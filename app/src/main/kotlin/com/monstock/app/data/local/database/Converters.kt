package com.monstock.app.data.local.database

import androidx.room.TypeConverter
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.LocalDate

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
}
