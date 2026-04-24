package com.monstock.app.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/** Holds a single in-progress batch-scan session.
 *  Injected as a Singleton so both [BarcodeScannerViewModel] and
 *  [BatchResultViewModel] share the same list of results. */
@Singleton
class BatchScanSession @Inject constructor() {

    private val _results = MutableStateFlow<List<BatchScanResult>>(emptyList())
    val results: StateFlow<List<BatchScanResult>> = _results

    fun add(result: BatchScanResult) {
        _results.value = _results.value + result
    }

    fun clear() {
        _results.value = emptyList()
    }
}

/** One entry in a batch-scan session. */
data class BatchScanResult(
    val barcode: String,
    /** Resolved product info, or null when the barcode was not found. */
    val info: ProductInfo?
)
