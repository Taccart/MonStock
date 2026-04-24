package com.monstock.app.ui.screens.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.monstock.app.domain.model.InventoryDiscrepancy
import com.monstock.app.ui.components.EmptyState
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryDiscrepancyScreen(
    sessionId: Long,
    navController: NavController,
    viewModel: InventoryDiscrepancyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Écarts d'inventaire") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${uiState.selectedCount} correction(s) sélectionnée(s)",
                    style = MaterialTheme.typography.bodySmall
                )
                Button(
                    onClick = {
                        viewModel.applyAndComplete {
                            navController.popBackStack(
                                route = "inventory",
                                inclusive = false
                            )
                        }
                    },
                    enabled = !uiState.isApplying
                ) {
                    if (uiState.isApplying) {
                        CircularProgressIndicator(modifier = Modifier.padding(end = 8.dp))
                    } else {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.padding(end = 4.dp))
                    }
                    Text("Appliquer et terminer")
                }
            }
        }
    ) { innerPadding ->
        if (uiState.discrepancies.isEmpty()) {
            EmptyState(
                icon = Icons.Default.Check,
                message = "Aucun écart détecté !\nTous les articles correspondent.",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.discrepancies, key = { it.entryId }) { discrepancy ->
                    DiscrepancyCard(
                        discrepancy = discrepancy,
                        onToggle = { viewModel.toggleSelection(discrepancy.entryId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DiscrepancyCard(
    discrepancy: InventoryDiscrepancy,
    onToggle: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (discrepancy.isPossiblyMissing)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (discrepancy.isPossiblyMissing) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = "Possiblement manquant",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(discrepancy.itemName, style = MaterialTheme.typography.bodyMedium)
                }
                Text(discrepancy.shelfName, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                if (discrepancy.isPossiblyMissing) {
                    Text("Non vérifié — possiblement manquant", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                } else {
                    if (discrepancy.hasQuantityDiscrepancy) {
                        Text(
                            text = "Qté : ${discrepancy.recordedQuantity} → ${discrepancy.countedQuantity} ${discrepancy.unit.name.lowercase()}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    if (discrepancy.hasExpiryDiscrepancy) {
                        Text(
                            text = "Exp. : ${discrepancy.recordedExpiryDate?.format(dateFormatter) ?: "—"} → ${discrepancy.correctedExpiryDate?.format(dateFormatter) ?: "—"}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Checkbox(
                checked = discrepancy.isSelected,
                onCheckedChange = { onToggle() }
            )
        }
    }
}

private val InventoryDiscrepancy.hasQuantityDiscrepancy: Boolean
    get() = countedQuantity != null && countedQuantity != recordedQuantity

private val InventoryDiscrepancy.hasExpiryDiscrepancy: Boolean
    get() = correctedExpiryDate != null && correctedExpiryDate != recordedExpiryDate
