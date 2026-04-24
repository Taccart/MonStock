package com.monstock.app.ui.screens.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.model.ConsumptionEventType
import com.monstock.app.data.repository.ConsumptionRepository
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.ConsumptionEvent
import com.monstock.app.domain.model.Item
import com.monstock.app.domain.model.Shelf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class ItemDetailUiState(
    val item: Item? = null,
    val shelf: Shelf? = null,
    val showDeleteDialog: Boolean = false,
    val deleted: Boolean = false,
    // Phase 4 — consumption logging dialog
    val showLogDialog: Boolean = false,
    val logEventType: ConsumptionEventType = ConsumptionEventType.CONSUMED,
    val logQuantity: String = "",
    val logQuantityError: Boolean = false,
    val logSaved: Boolean = false
)

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val shelfRepository: ShelfRepository,
    private val consumptionRepository: ConsumptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemDetailUiState())
    val uiState: StateFlow<ItemDetailUiState> = _uiState

    fun loadItem(itemId: Long) {
        viewModelScope.launch {
            val item = itemRepository.getItemById(itemId)
            _uiState.update { it.copy(item = item) }
            item?.let { i ->
                val shelf = shelfRepository.getShelfById(i.shelfId)
                _uiState.update { it.copy(shelf = shelf) }
            }
        }
    }

    fun requestDelete() = _uiState.update { it.copy(showDeleteDialog = true) }
    fun cancelDelete() = _uiState.update { it.copy(showDeleteDialog = false) }

    fun confirmDelete() {
        val item = _uiState.value.item ?: return
        viewModelScope.launch {
            itemRepository.deleteItem(item.id)
            _uiState.update { it.copy(showDeleteDialog = false, deleted = true) }
        }
    }

    // ── Phase 4 — consumption / waste logging ─────────────────────────────

    fun requestLogConsumed() = _uiState.update {
        it.copy(
            showLogDialog = true,
            logEventType = ConsumptionEventType.CONSUMED,
            logQuantity = it.item?.quantity?.toString() ?: "1",
            logQuantityError = false,
            logSaved = false
        )
    }

    fun requestLogWasted() = _uiState.update {
        it.copy(
            showLogDialog = true,
            logEventType = ConsumptionEventType.WASTED,
            logQuantity = it.item?.quantity?.toString() ?: "1",
            logQuantityError = false,
            logSaved = false
        )
    }

    fun onLogQuantityChange(v: String) =
        _uiState.update { it.copy(logQuantity = v, logQuantityError = false) }

    fun cancelLog() = _uiState.update { it.copy(showLogDialog = false) }

    fun confirmLog() {
        val state = _uiState.value
        val item = state.item ?: return
        val qty = state.logQuantity.toDoubleOrNull()
        if (qty == null || qty <= 0) {
            _uiState.update { it.copy(logQuantityError = true) }
            return
        }
        viewModelScope.launch {
            consumptionRepository.logEvent(
                ConsumptionEvent(
                    itemId = item.id,
                    itemName = item.name,
                    quantity = qty,
                    unit = item.unit,
                    category = item.category,
                    eventType = state.logEventType,
                    date = LocalDate.now(),
                    purchasePricePerUnit = item.purchasePricePerUnit
                )
            )
            // Decrement the item's quantity in stock
            val newQty = (item.quantity - qty).coerceAtLeast(0.0)
            itemRepository.updateItem(item.copy(quantity = newQty))
            _uiState.update { it.copy(showLogDialog = false, logSaved = true, item = item.copy(quantity = newQty)) }
        }
    }
}
