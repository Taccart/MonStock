package com.monstock.app.ui.screens.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.monstock.app.data.repository.InventoryRepository
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.domain.model.InventorySession
import com.monstock.app.ui.components.EmptyState
import com.monstock.app.util.InventoryExportHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

// ── ViewModel ─────────────────────────────────────────────────────────────────

data class InventoryHistoryDetailUiState(
    val session: InventorySession? = null
)

@HiltViewModel
class InventoryHistoryDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val sessionId: Long = checkNotNull(savedStateHandle["sessionId"])

    val entries: StateFlow<List<InventoryEntry>> =
        inventoryRepository.getEntriesForSession(sessionId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(InventoryHistoryDetailUiState())
    val uiState: StateFlow<InventoryHistoryDetailUiState> = _uiState

    init {
        viewModelScope.launch {
            val session = inventoryRepository.getSessionById(sessionId)
            _uiState.update { it.copy(session = session) }
        }
    }
}

// ── Screen ────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryHistoryDetailScreen(
    sessionId: Long,
    navController: NavController,
    viewModel: InventoryHistoryDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.session?.run { pantryName + (shelfName?.let { " — $it" } ?: "") } ?: "Rapport d'inventaire") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        uiState.session?.let { session ->
                            InventoryExportHelper.exportAndShare(context, session, entries)
                        }
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Exporter CSV")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Session summary header
            uiState.session?.let { session ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("Débuté le ${session.startedAt.format(dateTimeFormatter)}", style = MaterialTheme.typography.bodySmall)
                        session.completedAt?.let {
                            Text("Terminé le ${it.format(dateTimeFormatter)}", style = MaterialTheme.typography.bodySmall)
                        }
                        Text("${session.checkedItemCount} / ${session.totalItemsInScope} vérifiés · ${session.discrepancyCount} écart(s)", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            if (entries.isEmpty()) {
                EmptyState(icon = Icons.AutoMirrored.Filled.ArrowBack, message = "Aucun article dans cette session.")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(entries, key = { it.id }) { entry ->
                        HistoryEntryRow(entry = entry)
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryEntryRow(entry: InventoryEntry) {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(entry.itemName, style = MaterialTheme.typography.bodyMedium)
                Text(entry.shelfName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = "Enregistré : ${entry.recordedQuantity} ${entry.unit.name.lowercase()}" +
                            (entry.recordedExpiryDate?.let { " · Exp. ${it.format(dateFormatter)}" } ?: ""),
                    style = MaterialTheme.typography.bodySmall
                )
                if (entry.isChecked && entry.hasAnyDiscrepancy) {
                    Text(
                        text = "Compté : ${entry.countedQuantity ?: entry.recordedQuantity} ${entry.unit.name.lowercase()}" +
                                (entry.correctedExpiryDate?.let { " · Exp. ${it.format(dateFormatter)}" } ?: ""),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                if (!entry.isChecked) {
                    Text("Non vérifié", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
