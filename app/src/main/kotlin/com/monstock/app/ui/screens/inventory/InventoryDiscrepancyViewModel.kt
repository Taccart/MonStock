package com.monstock.app.ui.screens.inventory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.InventoryRepository
import com.monstock.app.domain.model.InventoryDiscrepancy
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.domain.model.InventorySession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InventoryDiscrepancyUiState(
    val session: InventorySession? = null,
    val discrepancies: List<InventoryDiscrepancy> = emptyList(),
    val isApplying: Boolean = false,
    val isApplied: Boolean = false
) {
    val selectedCount: Int get() = discrepancies.count { it.isSelected }
}

@HiltViewModel
class InventoryDiscrepancyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val sessionId: Long = checkNotNull(savedStateHandle["sessionId"])

    private val entries: StateFlow<List<InventoryEntry>> =
        inventoryRepository.getEntriesForSession(sessionId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(InventoryDiscrepancyUiState())
    val uiState: StateFlow<InventoryDiscrepancyUiState> = _uiState

    init {
        viewModelScope.launch {
            val session = inventoryRepository.getSessionById(sessionId)
            _uiState.update { it.copy(session = session) }
        }
        viewModelScope.launch {
            entries.collect { list ->
                _uiState.update { it.copy(discrepancies = buildDiscrepancies(list)) }
            }
        }
    }

    private fun buildDiscrepancies(entries: List<InventoryEntry>): List<InventoryDiscrepancy> =
        entries.filter { it.hasAnyDiscrepancy || !it.isChecked }.map { entry ->
            InventoryDiscrepancy(
                entryId = entry.id,
                itemId = entry.itemId,
                itemName = entry.itemName,
                shelfName = entry.shelfName,
                unit = entry.unit,
                recordedQuantity = entry.recordedQuantity,
                countedQuantity = entry.countedQuantity,
                recordedExpiryDate = entry.recordedExpiryDate,
                correctedExpiryDate = entry.correctedExpiryDate,
                isPossiblyMissing = !entry.isChecked,
                isSelected = entry.hasAnyDiscrepancy  // missing items unselected by default
            )
        }

    fun toggleSelection(entryId: Long) {
        _uiState.update { state ->
            state.copy(
                discrepancies = state.discrepancies.map { d ->
                    if (d.entryId == entryId) d.copy(isSelected = !d.isSelected) else d
                }
            )
        }
    }

    fun applyAndComplete(onDone: () -> Unit) {
        val discrepancies = _uiState.value.discrepancies
        viewModelScope.launch {
            _uiState.update { it.copy(isApplying = true) }
            inventoryRepository.completeAndApply(sessionId, discrepancies)
            _uiState.update { it.copy(isApplying = false, isApplied = true) }
            onDone()
        }
    }
}
