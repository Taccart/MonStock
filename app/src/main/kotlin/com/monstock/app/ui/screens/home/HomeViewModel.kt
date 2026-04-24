package com.monstock.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.data.repository.PantryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val totalItems: Int = 0,
    val expiringSoonCount: Int = 0,
    val expiredCount: Int = 0,
    val lowStockCount: Int = 0,
    val pantryCount: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    itemRepository: ItemRepository,
    pantryRepository: PantryRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        itemRepository.getTotalItemCount(),
        itemRepository.getItemsExpiringSoon(withinDays = 7),
        itemRepository.getExpiredItems(),
        itemRepository.getLowStockItems(),
        pantryRepository.getAllPantries()
    ) { total, expiringSoon, expired, lowStock, pantries ->
        HomeUiState(
            totalItems = total,
            expiringSoonCount = expiringSoon.size,
            expiredCount = expired.size,
            lowStockCount = lowStock.size,
            pantryCount = pantries.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )
}

