package com.monstock.app.data.remote.api

import com.monstock.app.data.remote.dto.OpenFoodFactsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodFactsApiService {

    @GET("api/v2/product/{barcode}?fields=product_name,product_name_fr,brands,categories_tags,quantity,image_front_url,image_url")
    suspend fun getProduct(@Path("barcode") barcode: String): OpenFoodFactsResponse
}
