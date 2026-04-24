package com.monstock.app.domain.model

data class Shelf(
    val id: Long = 0,
    val pantryId: Long,
    val name: String,
    val capacity: Int? = null
)
