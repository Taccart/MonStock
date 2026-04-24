package com.monstock.app.ui.screens.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.Item
import com.monstock.app.domain.model.Shelf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemDetailUiState(
    val item: Item? = null,
    val shelf: Shelf? = null,
    val showDeleteDialog: Boolean = false,
    val deleted: Boolean = false
)

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val shelfRepository: ShelfRepository
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
}
