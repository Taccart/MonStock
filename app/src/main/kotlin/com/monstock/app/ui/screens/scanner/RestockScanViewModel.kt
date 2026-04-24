package com.monstock.app.ui.screens.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.BarcodeRepository
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.Item
import com.monstock.app.domain.model.Shelf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class RestockScanUiState(
    val isLoading: Boolean = true,
    val item: Item? = null,
    val shelves: List<Shelf> = emptyList(),
    val selectedShelfId: Long? = null,
    val quantityDelta: String = "1",
    val newExpiryDate: LocalDate? = null,
    val showDatePicker: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RestockScanViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val shelfRepository: ShelfRepository,
    private val barcodeRepository: BarcodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RestockScanUiState())
    val uiState: StateFlow<RestockScanUiState> = _uiState

    fun loadByBarcode(barcode: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val item = itemRepository.getItemByBarcode(barcode)
            if (item == null) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Aucun article trouvé pour ce code-barres.")
                }
                return@launch
            }
            val allShelves = shelfRepository.getShelvesByPantry(
                // Load shelves from all pantries: collect once
                shelfRepository.getShelfById(item.shelfId)?.pantryId ?: return@launch
            ).first()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    item = item,
                    shelves = allShelves,
                    selectedShelfId = item.shelfId
                )
            }
        }
    }

    fun onQuantityDeltaChange(v: String) = _uiState.update { it.copy(quantityDelta = v) }
    fun onExpiryDateChange(date: LocalDate?) =
        _uiState.update { it.copy(newExpiryDate = date, showDatePicker = false) }
    fun onShelfChange(shelfId: Long) = _uiState.update { it.copy(selectedShelfId = shelfId) }
    fun toggleDatePicker() = _uiState.update { it.copy(showDatePicker = !it.showDatePicker) }

    fun save() {
        val state = _uiState.value
        val item = state.item ?: return
        val delta = state.quantityDelta.toDoubleOrNull() ?: 0.0
        val updated = item.copy(
            quantity = item.quantity + delta,
            expiryDate = state.newExpiryDate ?: item.expiryDate,
            shelfId = state.selectedShelfId ?: item.shelfId
        )
        viewModelScope.launch {
            itemRepository.updateItem(updated)
            _uiState.update { it.copy(saved = true) }
        }
    }
}
