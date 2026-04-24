package com.monstock.app.ui.screens.shelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.PantryRepository
import com.monstock.app.data.repository.ShelfRepository
import com.monstock.app.domain.model.Pantry
import com.monstock.app.domain.model.Shelf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShelfUiState(
    val pantry: Pantry? = null,
    val showAddEditDialog: Boolean = false,
    val editingShelf: Shelf? = null,
    val pendingDeleteShelf: Shelf? = null
)

@HiltViewModel
class ShelfViewModel @Inject constructor(
    private val shelfRepository: ShelfRepository,
    private val pantryRepository: PantryRepository
) : ViewModel() {

    private var pantryId: Long = -1L

    private val _uiState = MutableStateFlow(ShelfUiState())
    val uiState: StateFlow<ShelfUiState> = _uiState

    private val _shelves = MutableStateFlow<List<Shelf>>(emptyList())
    val shelves: StateFlow<List<Shelf>> = _shelves

    fun init(pantryId: Long) {
        if (this.pantryId == pantryId) return
        this.pantryId = pantryId
        viewModelScope.launch {
            pantryRepository.getPantryById(pantryId)?.let { p ->
                _uiState.update { it.copy(pantry = p) }
            }
        }
        viewModelScope.launch {
            shelfRepository.getShelvesByPantry(pantryId)
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
                .collect { _shelves.value = it }
        }
    }

    fun openAddDialog() {
        _uiState.update { it.copy(showAddEditDialog = true, editingShelf = null) }
    }

    fun openEditDialog(shelf: Shelf) {
        _uiState.update { it.copy(showAddEditDialog = true, editingShelf = shelf) }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(showAddEditDialog = false, editingShelf = null) }
    }

    fun saveShelf(name: String, capacity: Int?) {
        val editing = _uiState.value.editingShelf
        viewModelScope.launch {
            if (editing == null) {
                shelfRepository.insertShelf(Shelf(pantryId = pantryId, name = name, capacity = capacity))
            } else {
                shelfRepository.updateShelf(editing.copy(name = name, capacity = capacity))
            }
        }
        dismissDialog()
    }

    fun requestDelete(shelf: Shelf) {
        _uiState.update { it.copy(pendingDeleteShelf = shelf) }
    }

    fun confirmDelete() {
        val shelf = _uiState.value.pendingDeleteShelf ?: return
        viewModelScope.launch { shelfRepository.deleteShelf(shelf.id) }
        _uiState.update { it.copy(pendingDeleteShelf = null) }
    }

    fun cancelDelete() {
        _uiState.update { it.copy(pendingDeleteShelf = null) }
    }
}
