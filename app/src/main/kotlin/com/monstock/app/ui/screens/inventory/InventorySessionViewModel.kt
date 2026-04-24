package com.monstock.app.ui.screens.inventory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.InventoryRepository
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.domain.model.InventorySession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class InventorySessionUiState(
    val session: InventorySession? = null,
    val entries: List<InventoryEntry> = emptyList(),
    val checkingEntry: InventoryEntry? = null,
    val showCheckDialog: Boolean = false,
    val showCancelConfirm: Boolean = false,
    val errorMessage: String? = null
) {
    /** Entries grouped by shelf name for display */
    val entriesByShelf: Map<String, List<InventoryEntry>>
        get() = entries.groupBy { it.shelfName }

    val progress: Float
        get() = if (entries.isEmpty()) 0f
        else entries.count { it.isChecked }.toFloat() / entries.size.toFloat()

    val isComplete: Boolean
        get() = entries.isNotEmpty() && entries.all { it.isChecked }
}

@HiltViewModel
class InventorySessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val sessionId: Long = checkNotNull(savedStateHandle["sessionId"])

    val entries: StateFlow<List<InventoryEntry>> =
        inventoryRepository.getEntriesForSession(sessionId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(InventorySessionUiState())
    val uiState: StateFlow<InventorySessionUiState> = _uiState

    init {
        viewModelScope.launch {
            val session = inventoryRepository.getSessionById(sessionId)
            _uiState.update { it.copy(session = session) }
        }
        viewModelScope.launch {
            entries.collect { list ->
                _uiState.update { it.copy(entries = list) }
            }
        }
    }

    // ── Check item dialog ─────────────────────────────────────────────────────

    fun openCheckDialog(entry: InventoryEntry) {
        _uiState.update { it.copy(checkingEntry = entry, showCheckDialog = true) }
    }

    fun dismissCheckDialog() {
        _uiState.update { it.copy(checkingEntry = null, showCheckDialog = false) }
    }

    fun confirmCheck(
        entry: InventoryEntry,
        countedQuantity: Double?,
        correctedExpiryDate: LocalDate?,
        scannedViaBarcode: Boolean = false
    ) {
        viewModelScope.launch {
            inventoryRepository.checkEntry(
                entryId = entry.id,
                countedQuantity = countedQuantity,
                correctedExpiryDate = correctedExpiryDate,
                scannedViaBarcode = scannedViaBarcode
            )
            inventoryRepository.refreshSessionProgress(sessionId)
        }
        dismissCheckDialog()
    }

    // ── Barcode scan ──────────────────────────────────────────────────────────

    fun onBarcodeScanned(barcode: String) {
        viewModelScope.launch {
            val entry = inventoryRepository.findEntryByBarcode(sessionId, barcode)
            if (entry != null) {
                _uiState.update { it.copy(checkingEntry = entry, showCheckDialog = true) }
            } else {
                _uiState.update { it.copy(errorMessage = "Code-barres non trouvé dans la session : $barcode") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // ── Session controls ──────────────────────────────────────────────────────

    fun requestCancel() {
        _uiState.update { it.copy(showCancelConfirm = true) }
    }

    fun dismissCancelConfirm() {
        _uiState.update { it.copy(showCancelConfirm = false) }
    }

    fun cancelSession(onDone: () -> Unit) {
        viewModelScope.launch {
            inventoryRepository.cancelSession(sessionId)
            onDone()
        }
    }
}
