package com.monstock.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.monstock.app.data.model.InventorySessionStatus
import java.time.LocalDateTime

@Entity(
    tableName = "inventory_sessions",
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
data class InventorySessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pantryId: Long,
    val pantryName: String,
    /** Null means the session covers the whole pantry. */
    val shelfId: Long? = null,
    val shelfName: String? = null,
    val status: InventorySessionStatus = InventorySessionStatus.IN_PROGRESS,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime? = null,
    val notes: String? = null,
    val totalItemsInScope: Int = 0,
    val checkedItemCount: Int = 0,
    val discrepancyCount: Int = 0
)
