package com.monstock.app.ui.screens.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.PantryRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.Item
import com.monstock.app.domain.model.Pantry
import com.monstock.app.domain.model.Shelf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AddEditItemUiState(
    // Form fields
    val name: String = "",
    val brand: String = "",
    val category: ItemCategory = ItemCategory.OTHER,
    val quantity: String = "1",
    val unit: ItemUnit = ItemUnit.PIECE,
    val purchaseDate: LocalDate? = null,
    val expiryDate: LocalDate? = null,
    val minimumStockThreshold: String = "",
    val barcode: String = "",
    val notes: String = "",
    // Shelf selection
    val selectedShelfId: Long? = null,
    val selectedPantryId: Long? = null,
    val pantries: List<Pantry> = emptyList(),
    val shelves: List<Shelf> = emptyList(),
    // Meta
    val isEditMode: Boolean = false,
    val saved: Boolean = false,
    val nameError: Boolean = false,
    val shelfError: Boolean = false
)

@HiltViewModel
class AddEditItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val shelfRepository: ShelfRepository,
    private val pantryRepository: PantryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditItemUiState())
    val uiState: StateFlow<AddEditItemUiState> = _uiState

    val allPantries: StateFlow<List<Pantry>> = pantryRepository.getAllPantries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun init(itemId: Long?, shelfId: Long?) {
        viewModelScope.launch {
            // Load pantries for shelf picker
            pantryRepository.getAllPantries().collect { pantries ->
                _uiState.update { it.copy(pantries = pantries) }
            }
        }

        if (itemId != null) {
            viewModelScope.launch {
                val item = itemRepository.getItemById(itemId) ?: return@launch
                val shelf = shelfRepository.getShelfById(item.shelfId)
                _uiState.update {
                    it.copy(
                        isEditMode = true,
                        name = item.name,
                        brand = item.brand ?: "",
                        category = item.category,
                        quantity = item.quantity.toString(),
                        unit = item.unit,
                        purchaseDate = item.purchaseDate,
                        expiryDate = item.expiryDate,
                        minimumStockThreshold = item.minimumStockThreshold?.toString() ?: "",
                        barcode = item.barcode ?: "",
                        notes = item.notes ?: "",
                        selectedShelfId = item.shelfId,
                        selectedPantryId = shelf?.pantryId
                    )
                }
                shelf?.pantryId?.let { loadShelvesForPantry(it) }
            }
        } else if (shelfId != null) {
            viewModelScope.launch {
                val shelf = shelfRepository.getShelfById(shelfId)
                _uiState.update { it.copy(selectedShelfId = shelfId, selectedPantryId = shelf?.pantryId) }
                shelf?.pantryId?.let { loadShelvesForPantry(it) }
            }
        }
    }

    fun loadShelvesForPantry(pantryId: Long) {
        _uiState.update { it.copy(selectedPantryId = pantryId, selectedShelfId = null) }
        viewModelScope.launch {
            shelfRepository.getShelvesByPantry(pantryId).collect { shelves ->
                _uiState.update { it.copy(shelves = shelves) }
            }
        }
    }

    fun onNameChange(v: String) = _uiState.update { it.copy(name = v, nameError = false) }
    fun onBrandChange(v: String) = _uiState.update { it.copy(brand = v) }
    fun onCategoryChange(v: ItemCategory) = _uiState.update { it.copy(category = v) }
    fun onQuantityChange(v: String) = _uiState.update { it.copy(quantity = v) }
    fun onUnitChange(v: ItemUnit) = _uiState.update { it.copy(unit = v) }
    fun onPurchaseDateChange(v: LocalDate?) = _uiState.update { it.copy(purchaseDate = v) }
    fun onExpiryDateChange(v: LocalDate?) = _uiState.update { it.copy(expiryDate = v) }
    fun onMinThresholdChange(v: String) = _uiState.update { it.copy(minimumStockThreshold = v) }
    fun onBarcodeChange(v: String) = _uiState.update { it.copy(barcode = v) }
    fun onNotesChange(v: String) = _uiState.update { it.copy(notes = v) }
    fun onShelfSelected(shelfId: Long) = _uiState.update { it.copy(selectedShelfId = shelfId, shelfError = false) }

    fun save(existingItemId: Long?) {
        val state = _uiState.value
        var hasError = false

        if (state.name.isBlank()) {
            _uiState.update { it.copy(nameError = true) }
            hasError = true
        }
        if (state.selectedShelfId == null) {
            _uiState.update { it.copy(shelfError = true) }
            hasError = true
        }
        if (hasError) return

        val item = Item(
            id = existingItemId ?: 0L,
            shelfId = state.selectedShelfId!!,
            name = state.name.trim(),
            brand = state.brand.trim().ifBlank { null },
            category = state.category,
            quantity = state.quantity.toDoubleOrNull() ?: 1.0,
            unit = state.unit,
            purchaseDate = state.purchaseDate,
            expiryDate = state.expiryDate,
            minimumStockThreshold = state.minimumStockThreshold.toDoubleOrNull(),
            barcode = state.barcode.trim().ifBlank { null },
            notes = state.notes.trim().ifBlank { null }
        )

        viewModelScope.launch {
            if (existingItemId == null) itemRepository.insertItem(item)
            else itemRepository.updateItem(item)
            _uiState.update { it.copy(saved = true) }
        }
    }
}
