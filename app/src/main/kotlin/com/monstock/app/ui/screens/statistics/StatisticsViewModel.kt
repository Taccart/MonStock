package com.monstock.app.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.local.dao.ItemConsumptionSummary
import com.monstock.app.data.repository.ConsumptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

data class StatisticsUiState(
    val totalConsumed: Int = 0,
    val totalWasted: Int = 0,
    val mostConsumed: List<ItemConsumptionSummary> = emptyList(),
    val currentMonthSpend: Double? = null
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val consumptionRepository: ConsumptionRepository
) : ViewModel() {

    private val now = LocalDate.now()

    val uiState: StateFlow<StatisticsUiState> = combine(
        consumptionRepository.getTotalConsumedCount(),
        consumptionRepository.getTotalWastedCount(),
        consumptionRepository.getMostConsumedItems(),
        consumptionRepository.getMonthlySpend(now.year, now.monthValue)
    ) { consumed, wasted, mostConsumed, monthSpend ->
        StatisticsUiState(
            totalConsumed = consumed,
            totalWasted = wasted,
            mostConsumed = mostConsumed,
            currentMonthSpend = monthSpend
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StatisticsUiState()
    )
}
