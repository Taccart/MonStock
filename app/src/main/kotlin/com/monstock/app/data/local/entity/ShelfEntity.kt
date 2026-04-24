package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "shelves",
    foreignKeys = [
        ForeignKey(
            entity = PantryEntity::class,
            parentColumns = ["id"],
            childColumns = ["pantryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("pantryId")]
)
data class ShelfEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pantryId: Long,
    val name: String,
    val capacity: Int? = null
)
