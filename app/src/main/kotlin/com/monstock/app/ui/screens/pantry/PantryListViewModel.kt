package com.monstock.app.ui.screens.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.PantryRepository
import com.monstock.app.domain.model.Pantry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PantryListUiState(
    val pantries: List<Pantry> = emptyList(),
    val showAddEditDialog: Boolean = false,
    val editingPantry: Pantry? = null,
    val pendingDeletePantry: Pantry? = null
)

@HiltViewModel
class PantryListViewModel @Inject constructor(
    private val pantryRepository: PantryRepository
) : ViewModel() {

    val pantries: StateFlow<List<Pantry>> = pantryRepository.getAllPantries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(PantryListUiState())
    val uiState: StateFlow<PantryListUiState> = _uiState

    fun openAddDialog() {
        _uiState.update { it.copy(showAddEditDialog = true, editingPantry = null) }
    }

    fun openEditDialog(pantry: Pantry) {
        _uiState.update { it.copy(showAddEditDialog = true, editingPantry = pantry) }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(showAddEditDialog = false, editingPantry = null) }
    }

    fun savePantry(name: String) {
        val editing = _uiState.value.editingPantry
        viewModelScope.launch {
            if (editing == null) {
                pantryRepository.insertPantry(Pantry(name = name))
            } else {
                pantryRepository.updatePantry(editing.copy(name = name))
            }
        }
        dismissDialog()
    }

    fun requestDelete(pantry: Pantry) {
        _uiState.update { it.copy(pendingDeletePantry = pantry) }
    }

    fun confirmDelete() {
        val pantry = _uiState.value.pendingDeletePantry ?: return
        viewModelScope.launch { pantryRepository.deletePantry(pantry.id) }
        _uiState.update { it.copy(pendingDeletePantry = null) }
    }

    fun cancelDelete() {
        _uiState.update { it.copy(pendingDeletePantry = null) }
    }
}
