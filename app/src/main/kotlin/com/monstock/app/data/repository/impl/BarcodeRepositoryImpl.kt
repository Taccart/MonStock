package com.monstock.app.data.repository.impl

import com.monstock.app.data.local.dao.BarcodeCacheDao
import com.monstock.app.data.local.entity.BarcodeCacheEntity
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import com.monstock.app.data.remote.api.OpenFoodFactsApiService
import com.monstock.app.data.repository.BarcodeRepository
import com.monstock.app.data.repository.ProductInfo
import javax.inject.Inject

class BarcodeRepositoryImpl @Inject constructor(
    private val cacheDao: BarcodeCacheDao,
    private val apiService: OpenFoodFactsApiService
) : BarcodeRepository {

    override suspend fun lookupBarcode(barcode: String): ProductInfo? {
        // 1. Check local cache first
        cacheDao.getByBarcode(barcode)?.let { cached ->
            return ProductInfo(
                barcode = cached.barcode,
                name = cached.name,
                brand = cached.brand,
                category = cached.category?.let { mapCategory(it) } ?: ItemCategory.OTHER,
                unit = cached.unit?.let { mapUnit(it) } ?: ItemUnit.PIECE,
                imageUrl = cached.imageUrl,
                fromCache = true
            )
        }

        // 2. Query Open Food Facts
        return try {
            val response = apiService.getProduct(barcode)
            if (response.status == 1 && response.product != null) {
                val product = response.product
                val category = mapCategoriesTags(product.categoriesTags)
                val info = ProductInfo(
                    barcode = barcode,
                    name = product.bestName ?: return null,
                    brand = product.firstBrand,
                    category = category,
                    unit = mapQuantityToUnit(product.quantity),
                    imageUrl = product.bestImageUrl,
                    fromCache = false
                )
                // Persist to cache
                cacheProduct(info)
                info
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun cacheProduct(info: ProductInfo) {
        cacheDao.insert(
            BarcodeCacheEntity(
                barcode = info.barcode,
                name = info.name,
                brand = info.brand,
                category = info.category.name,
                unit = info.unit.name,
                imageUrl = info.imageUrl
            )
        )
    }

    override suspend fun evictOldCache(maxAgeMs: Long) {
        cacheDao.deleteOlderThan(System.currentTimeMillis() - maxAgeMs)
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private fun mapCategoriesTags(tags: List<String>?): ItemCategory {
        if (tags.isNullOrEmpty()) return ItemCategory.OTHER
        for (tag in tags) {
            val lower = tag.lowercase()
            when {
                lower.contains("dairy") || lower.contains("laitier") || lower.contains("fromage") -> return ItemCategory.DAIRY
                lower.contains("vegetable") || lower.contains("legume") -> return ItemCategory.VEGETABLES
                lower.contains("fruit") -> return ItemCategory.FRUITS
                lower.contains("meat") || lower.contains("viande") -> return ItemCategory.MEAT
                lower.contains("fish") || lower.contains("seafood") || lower.contains("poisson") -> return ItemCategory.FISH
                lower.contains("canned") || lower.contains("conserve") -> return ItemCategory.CANNED
                lower.contains("beverage") || lower.contains("boisson") || lower.contains("drink") -> return ItemCategory.BEVERAGES
                lower.contains("frozen") || lower.contains("surgelé") -> return ItemCategory.FROZEN
                lower.contains("bak") || lower.contains("bread") || lower.contains("pain") || lower.contains("pastry") -> return ItemCategory.BAKERY
                lower.contains("condiment") || lower.contains("sauce") || lower.contains("spice") -> return ItemCategory.CONDIMENTS
                lower.contains("snack") || lower.contains("biscuit") || lower.contains("chip") -> return ItemCategory.SNACKS
                lower.contains("hygiene") || lower.contains("hygiène") || lower.contains("cosmetic") -> return ItemCategory.HYGIENE
            }
        }
        return ItemCategory.OTHER
    }

    private fun mapQuantityToUnit(quantity: String?): ItemUnit {
        if (quantity.isNullOrBlank()) return ItemUnit.PIECE
        val lower = quantity.lowercase()
        return when {
            lower.contains("kg") || lower.contains("g ") || lower.contains("gr") -> ItemUnit.KG
            lower.contains("l ") || lower.contains("ml") || lower.contains("cl") || lower.contains("litre") -> ItemUnit.LITER
            lower.contains("boîte") || lower.contains("boite") || lower.contains("can") -> ItemUnit.CAN
            lower.contains("bouteille") || lower.contains("bottle") -> ItemUnit.BOTTLE
            lower.contains("sachet") || lower.contains("packet") || lower.contains("paquet") -> ItemUnit.PACKET
            else -> ItemUnit.PIECE
        }
    }

    private fun mapCategory(name: String): ItemCategory =
        runCatching { ItemCategory.valueOf(name) }.getOrDefault(ItemCategory.OTHER)

    private fun mapUnit(name: String): ItemUnit =
        runCatching { ItemUnit.valueOf(name) }.getOrDefault(ItemUnit.PIECE)
}
