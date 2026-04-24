package com.monstock.app.ui.screens.itemlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.Item
import com.monstock.app.domain.model.Shelf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOrder { EXPIRY, NAME, CATEGORY, QUANTITY }
enum class ExpiryFilter { ALL, EXPIRING_SOON, EXPIRED, OK }

data class ItemListUiState(
    val shelf: Shelf? = null,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.EXPIRY,
    val categoryFilter: ItemCategory? = null,
    val expiryFilter: ExpiryFilter = ExpiryFilter.ALL,
    val pendingDeleteItem: Item? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val shelfRepository: ShelfRepository
) : ViewModel() {

    private val _shelfId = MutableStateFlow<Long?>(null)
    private val _uiState = MutableStateFlow(ItemListUiState())
    val uiState: StateFlow<ItemListUiState> = _uiState

    private val _rawItems: StateFlow<List<Item>> = _shelfId.flatMapLatest { shelfId ->
        if (shelfId == null) itemRepository.getAllItems()
        else itemRepository.getItemsByShelf(shelfId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val items: StateFlow<List<Item>> = combine(_rawItems, _uiState) { raw, state ->
        var result = raw

        // search filter
        if (state.searchQuery.isNotBlank()) {
            val q = state.searchQuery.lowercase()
            result = result.filter {
                it.name.lowercase().contains(q) || it.brand?.lowercase()?.contains(q) == true
            }
        }

        // category filter
        state.categoryFilter?.let { cat ->
            result = result.filter { it.category == cat }
        }

        // expiry filter
        result = when (state.expiryFilter) {
            ExpiryFilter.ALL -> result
            ExpiryFilter.EXPIRING_SOON -> result.filter { it.isExpiringSoon() && !it.isExpired }
            ExpiryFilter.EXPIRED -> result.filter { it.isExpired }
            ExpiryFilter.OK -> result.filter { !it.isExpired && !it.isExpiringSoon() }
        }

        // sort
        result = when (state.sortOrder) {
            SortOrder.EXPIRY -> result.sortedWith(compareBy(nullsLast()) { it.expiryDate })
            SortOrder.NAME -> result.sortedBy { it.name.lowercase() }
            SortOrder.CATEGORY -> result.sortedBy { it.category.name }
            SortOrder.QUANTITY -> result.sortedBy { it.quantity }
        }

        result
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun init(shelfId: Long?) {
        _shelfId.value = shelfId
        shelfId?.let { id ->
            viewModelScope.launch {
                shelfRepository.getShelfById(id)?.let { shelf ->
                    _uiState.update { it.copy(shelf = shelf) }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) = _uiState.update { it.copy(searchQuery = query) }
    fun onSortOrderChange(order: SortOrder) = _uiState.update { it.copy(sortOrder = order) }
    fun onCategoryFilterChange(cat: ItemCategory?) = _uiState.update { it.copy(categoryFilter = cat) }
    fun onExpiryFilterChange(filter: ExpiryFilter) = _uiState.update { it.copy(expiryFilter = filter) }

    fun requestDelete(item: Item) = _uiState.update { it.copy(pendingDeleteItem = item) }
    fun cancelDelete() = _uiState.update { it.copy(pendingDeleteItem = null) }
    fun confirmDelete() {
        val item = _uiState.value.pendingDeleteItem ?: return
        viewModelScope.launch { itemRepository.deleteItem(item.id) }
        _uiState.update { it.copy(pendingDeleteItem = null) }
    }
}
