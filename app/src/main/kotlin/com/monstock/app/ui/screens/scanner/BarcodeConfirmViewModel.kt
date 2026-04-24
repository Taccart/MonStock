package com.monstock.app.ui.screens.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import com.monstock.app.data.repository.BarcodeRepository
import com.monstock.app.data.repository.ProductInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BarcodeConfirmUiState(
    val isLoading: Boolean = true,
    val barcode: String = "",
    val name: String = "",
    val brand: String = "",
    val category: ItemCategory = ItemCategory.OTHER,
    val unit: ItemUnit = ItemUnit.PIECE,
    val imageUrl: String? = null,
    val error: String? = null,
    val confirmed: Boolean = false,
    /** True if the product was not found in OFF – user entered the barcode manually */
    val isUnknownProduct: Boolean = false
)

@HiltViewModel
class BarcodeConfirmViewModel @Inject constructor(
    private val barcodeRepository: BarcodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarcodeConfirmUiState())
    val uiState: StateFlow<BarcodeConfirmUiState> = _uiState

    fun load(barcode: String) {
        _uiState.update { it.copy(isLoading = true, barcode = barcode) }
        viewModelScope.launch {
            val info = barcodeRepository.lookupBarcode(barcode)
            if (info != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        name = info.name,
                        brand = info.brand ?: "",
                        category = info.category,
                        unit = info.unit,
                        imageUrl = info.imageUrl
                    )
                }
            } else {
                _uiState.update {
                    it.copy(isLoading = false, isUnknownProduct = true)
                }
            }
        }
    }

    fun onNameChange(v: String) = _uiState.update { it.copy(name = v) }
    fun onBrandChange(v: String) = _uiState.update { it.copy(brand = v) }
    fun onCategoryChange(v: ItemCategory) = _uiState.update { it.copy(category = v) }
    fun onUnitChange(v: ItemUnit) = _uiState.update { it.copy(unit = v) }

    /** Returns the confirmed ProductInfo so the calling screen can pass it to AddEdit. */
    fun confirm(): ProductInfo {
        val state = _uiState.value
        val info = ProductInfo(
            barcode = state.barcode,
            name = state.name,
            brand = state.brand.ifBlank { null },
            category = state.category,
            unit = state.unit,
            imageUrl = state.imageUrl
        )
        viewModelScope.launch { barcodeRepository.cacheProduct(info) }
        _uiState.update { it.copy(confirmed = true) }
        return info
    }
}
