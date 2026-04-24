package com.monstock.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenFoodFactsResponse(
    @SerializedName("status") val status: Int,              // 1 = found, 0 = not found
    @SerializedName("product") val product: OpenFoodFactsProduct?
)

data class OpenFoodFactsProduct(
    @SerializedName("product_name") val productName: String?,
    @SerializedName("product_name_fr") val productNameFr: String?,
    @SerializedName("brands") val brands: String?,
    @SerializedName("categories_tags") val categoriesTags: List<String>?,
    @SerializedName("quantity") val quantity: String?,
    @SerializedName("image_front_url") val imageFrontUrl: String?,
    @SerializedName("image_url") val imageUrl: String?
) {
    /** Best display name: FR fallback to generic. */
    val bestName: String?
        get() = productNameFr?.takeIf { it.isNotBlank() } ?: productName?.takeIf { it.isNotBlank() }

    /** First brand from comma-separated list. */
    val firstBrand: String?
        get() = brands?.split(",")?.firstOrNull()?.trim()?.takeIf { it.isNotBlank() }

    /** Best available image URL. */
    val bestImageUrl: String?
        get() = imageFrontUrl?.takeIf { it.isNotBlank() } ?: imageUrl?.takeIf { it.isNotBlank() }
}
