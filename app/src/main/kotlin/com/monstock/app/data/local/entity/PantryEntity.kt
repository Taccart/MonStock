package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pantries")
data class PantryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)
