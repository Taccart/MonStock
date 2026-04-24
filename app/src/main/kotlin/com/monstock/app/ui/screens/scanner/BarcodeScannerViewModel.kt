package com.monstock.app.ui.screens.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.BarcodeRepository
import com.monstock.app.data.repository.ProductInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ScannerMode { ADD_ITEM, RESTOCK }

sealed interface ScannerEvent {
    data class ProductFound(val info: ProductInfo) : ScannerEvent
    data class ProductNotFound(val barcode: String) : ScannerEvent
    data class RestockTarget(val info: ProductInfo) : ScannerEvent
}

data class BarcodeScannerUiState(
    val isScanning: Boolean = true,
    val isFlashlightOn: Boolean = false,
    val isBatchMode: Boolean = false,
    val isLoading: Boolean = false,
    val manualBarcode: String = "",
    val showManualEntry: Boolean = false,
    val error: String? = null,
    val event: ScannerEvent? = null,
    /** Barcodes already processed during batch mode session */
    val processedBarcodes: Set<String> = emptySet(),
    val batchResults: List<ProductInfo> = emptyList()
)

@HiltViewModel
class BarcodeScannerViewModel @Inject constructor(
    private val barcodeRepository: BarcodeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarcodeScannerUiState())
    val uiState: StateFlow<BarcodeScannerUiState> = _uiState

    fun onBarcodeDetected(barcode: String, mode: ScannerMode) {
        val state = _uiState.value
        // Ignore if already processing or barcode already handled in batch
        if (state.isLoading) return
        if (state.processedBarcodes.contains(barcode)) return

        _uiState.update { it.copy(isLoading = true, isScanning = false) }
        viewModelScope.launch {
            val info = barcodeRepository.lookupBarcode(barcode)
            _uiState.update { current ->
                val processed = current.processedBarcodes + barcode
                when {
                    info != null && mode == ScannerMode.ADD_ITEM -> current.copy(
                        isLoading = false,
                        processedBarcodes = processed,
                        event = ScannerEvent.ProductFound(info)
                    )
                    info != null && mode == ScannerMode.RESTOCK -> current.copy(
                        isLoading = false,
                        processedBarcodes = processed,
                        event = ScannerEvent.RestockTarget(info)
                    )
                    else -> current.copy(
                        isLoading = false,
                        processedBarcodes = processed,
                        event = ScannerEvent.ProductNotFound(barcode)
                    )
                }
            }
        }
    }

    fun onManualBarcodeChange(value: String) =
        _uiState.update { it.copy(manualBarcode = value) }

    fun submitManualBarcode(mode: ScannerMode) {
        val barcode = _uiState.value.manualBarcode.trim()
        if (barcode.isBlank()) return
        _uiState.update { it.copy(showManualEntry = false) }
        onBarcodeDetected(barcode, mode)
    }

    fun toggleFlashlight() =
        _uiState.update { it.copy(isFlashlightOn = !it.isFlashlightOn) }

    fun toggleBatchMode() =
        _uiState.update { it.copy(isBatchMode = !it.isBatchMode) }

    fun toggleManualEntry() =
        _uiState.update { it.copy(showManualEntry = !it.showManualEntry, manualBarcode = "") }

    fun resumeScanning() =
        _uiState.update { it.copy(isScanning = true, event = null, error = null) }

    fun consumeEvent() =
        _uiState.update { it.copy(event = null, isScanning = if (it.isBatchMode) true else false) }
}
