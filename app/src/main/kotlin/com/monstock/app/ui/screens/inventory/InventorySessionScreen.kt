package com.monstock.app.ui.screens.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.ui.components.ConfirmDeleteDialog
import com.monstock.app.ui.navigation.Screen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventorySessionScreen(
    sessionId: Long,
    navController: NavController,
    viewModel: InventorySessionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Listen for barcode scanned in inventory mode (returned from scanner)
    val scannedBarcode = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("scanned_barcode", "")
        ?.collectAsStateWithLifecycle()
    LaunchedEffect(scannedBarcode?.value) {
        val barcode = scannedBarcode?.value ?: return@LaunchedEffect
        if (barcode.isNotBlank()) {
            viewModel.onBarcodeScanned(barcode)
            navController.currentBackStackEntry?.savedStateHandle?.set("scanned_barcode", "")
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(uiState.session?.run { pantryName + (shelfName?.let { " — $it" } ?: "") } ?: "Inventaire")
                        if (uiState.entries.isNotEmpty()) {
                            Text(
                                text = "${uiState.entries.count { it.isChecked }} / ${uiState.entries.size} vérifiés",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::requestCancel) {
                        Icon(Icons.Default.Close, contentDescription = "Annuler la session")
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.isComplete) {
                ExtendedFloatingActionButton(
                    text = { Text("Voir les écarts") },
                    icon = { Icon(Icons.Default.Check, contentDescription = null) },
                    onClick = { navController.navigate(Screen.InventoryDiscrepancy.createRoute(sessionId)) }
                )
            } else {
                ExtendedFloatingActionButton(
                    text = { Text("Scanner") },
                    icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = null) },
                    onClick = { navController.navigate(Screen.BarcodeScanner.createRoute(com.monstock.app.ui.screens.scanner.ScannerMode.INVENTORY)) }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Progress bar
            LinearProgressIndicator(
                progress = { uiState.progress },
                modifier = Modifier.fillMaxWidth()
            )

            // Shelf-grouped entries
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.entriesByShelf.forEach { (shelfName, shelfEntries) ->
                    item(key = "header_$shelfName") {
                        ShelfHeader(name = shelfName, entries = shelfEntries)
                    }
                    items(shelfEntries, key = { it.id }) { entry ->
                        InventoryEntryCard(
                            entry = entry,
                            onClick = { viewModel.openCheckDialog(entry) }
                        )
                    }
                }
            }
        }

        // Check dialog
        if (uiState.showCheckDialog) {
            uiState.checkingEntry?.let { entry ->
                CheckEntryDialog(
                    entry = entry,
                    onConfirm = { qty, expiry ->
                        viewModel.confirmCheck(entry, qty, expiry)
                    },
                    onDismiss = viewModel::dismissCheckDialog
                )
            }
        }

        // Cancel confirm dialog
        if (uiState.showCancelConfirm) {
            ConfirmDeleteDialog(
                title = "Annuler l'inventaire",
                message = "Annuler cette session ? Aucune modification ne sera appliquée.",
                onConfirm = { viewModel.cancelSession { navController.popBackStack() } },
                onDismiss = viewModel::dismissCancelConfirm
            )
        }
    }
}

@Composable
private fun ShelfHeader(name: String, entries: List<InventoryEntry>) {
    val checkedCount = entries.count { it.isChecked }
    val isShelfDone = checkedCount == entries.size
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = name, style = MaterialTheme.typography.titleSmall)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "$checkedCount/${entries.size}", style = MaterialTheme.typography.labelSmall)
            if (isShelfDone) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Étagère complète",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun InventoryEntryCard(entry: InventoryEntry, onClick: () -> Unit) {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when {
                entry.isChecked && entry.hasAnyDiscrepancy -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                entry.isChecked -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(entry.itemName, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = buildString {
                        append("Enregistré : ${entry.recordedQuantity} ${entry.unit.name.lowercase()}")
                        entry.recordedExpiryDate?.let { append(" · Exp. ${it.format(dateFormatter)}") }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (entry.isChecked) {
                    Text(
                        text = buildString {
                            append("Compté : ${entry.countedQuantity ?: entry.recordedQuantity} ${entry.unit.name.lowercase()}")
                            entry.correctedExpiryDate?.let { append(" · Exp. ${it.format(dateFormatter)}") }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = if (entry.hasAnyDiscrepancy) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
                    )
                }
            }
            if (entry.isChecked) {
                Icon(Icons.Default.Check, contentDescription = "Vérifié", tint = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
private fun CheckEntryDialog(
    entry: InventoryEntry,
    onConfirm: (countedQty: Double?, correctedExpiry: LocalDate?) -> Unit,
    onDismiss: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

    var qtyText by remember { mutableStateOf(entry.recordedQuantity.toString()) }
    var expiryText by remember { mutableStateOf(entry.correctedExpiryDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: entry.recordedExpiryDate?.format(DateTimeFormatter.ISO_LOCAL_DATE) ?: "") }
    var qtyError by remember { mutableStateOf(false) }
    var expiryError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(entry.itemName) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Enregistré : ${entry.recordedQuantity} ${entry.unit.name.lowercase()}" +
                            (entry.recordedExpiryDate?.let { " · Exp. ${it.format(dateFormatter)}" } ?: ""),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = qtyText,
                    onValueChange = { qtyText = it; qtyError = false },
                    label = { Text("Quantité comptée") },
                    isError = qtyError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = expiryText,
                    onValueChange = { expiryText = it; expiryError = false },
                    label = { Text("Date d'expiration (AAAA-MM-JJ)") },
                    isError = expiryError,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val qty = qtyText.toDoubleOrNull()
                if (qtyText.isNotBlank() && qty == null) { qtyError = true; return@Button }
                val expiry = if (expiryText.isNotBlank()) {
                    runCatching { LocalDate.parse(expiryText, DateTimeFormatter.ISO_LOCAL_DATE) }.getOrElse { expiryError = true; return@Button }
                } else null
                onConfirm(qty, expiry)
            }) { Text("Confirmer") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}
