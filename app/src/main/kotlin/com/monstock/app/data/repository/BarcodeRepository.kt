package com.monstock.app.data.repository

import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit

/** Resolved product info from a barcode lookup (API or cache). */
data class ProductInfo(
    val barcode: String,
    val name: String,
    val brand: String?,
    val category: ItemCategory,
    val unit: ItemUnit,
    val imageUrl: String?,
    val fromCache: Boolean = false
)

interface BarcodeRepository {

    /** Look up a barcode: checks local cache first, then Open Food Facts.
     *  Returns null when the product is not found or network is unavailable. */
    suspend fun lookupBarcode(barcode: String): ProductInfo?

    /** Persist a product lookup result to the local cache. */
    suspend fun cacheProduct(info: ProductInfo)

    /** Evict cache entries older than [maxAgeMs] milliseconds. */
    suspend fun evictOldCache(maxAgeMs: Long = 30L * 24 * 60 * 60 * 1000)
}
