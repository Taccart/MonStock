package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barcode_cache")
data class BarcodeCacheEntity(
    @PrimaryKey val barcode: String,
    val name: String,
    val brand: String?,
    val category: String?,
    val unit: String?,
    val imageUrl: String?,
    val cachedAt: Long = System.currentTimeMillis()
)
