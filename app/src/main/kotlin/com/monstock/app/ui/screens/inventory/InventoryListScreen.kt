package com.monstock.app.ui.screens.inventory

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.monstock.app.data.model.InventorySessionStatus
import com.monstock.app.domain.model.InventorySession
import com.monstock.app.domain.model.Pantry
import com.monstock.app.domain.model.Shelf
import com.monstock.app.ui.components.EmptyState
import com.monstock.app.ui.navigation.Screen
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryListScreen(
    navController: NavController,
    viewModel: InventoryListViewModel = hiltViewModel()
) {
    val sessions by viewModel.sessions.collectAsStateWithLifecycle()
    val pantries by viewModel.pantries.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventaires") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::openStartDialog) {
                Icon(Icons.Default.Add, contentDescription = "Nouvel inventaire")
            }
        }
    ) { innerPadding ->
        if (sessions.isEmpty()) {
            EmptyState(
                icon = Icons.Default.Inventory,
                message = "Aucun inventaire.\nAppuyez sur + pour démarrer.",
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
                items(sessions, key = { it.id }) { session ->
                    InventorySessionCard(
                        session = session,
                        onClick = {
                            when (session.status) {
                                InventorySessionStatus.IN_PROGRESS ->
                                    navController.navigate(Screen.InventorySession.createRoute(session.id))
                                else ->
                                    navController.navigate(Screen.InventoryHistoryDetail.createRoute(session.id))
                            }
                        }
                    )
                }
            }
        }

        if (uiState.showStartDialog) {
            StartInventoryDialog(
                pantries = pantries,
                shelves = uiState.shelves,
                selectedPantryId = uiState.selectedPantryId,
                selectedShelfId = uiState.selectedShelfId,
                isLoading = uiState.isLoading,
                onSelectPantry = viewModel::selectPantry,
                onSelectShelf = viewModel::selectShelf,
                onConfirm = {
                    viewModel.startSession { sessionId ->
                        navController.navigate(Screen.InventorySession.createRoute(sessionId))
                    }
                },
                onDismiss = viewModel::dismissStartDialog
            )
        }
    }
}

@Composable
private fun InventorySessionCard(
    session: InventorySession,
    onClick: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (session.status) {
                InventorySessionStatus.IN_PROGRESS -> MaterialTheme.colorScheme.primaryContainer
                InventorySessionStatus.COMPLETED -> MaterialTheme.colorScheme.secondaryContainer
                InventorySessionStatus.CANCELLED -> MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = session.pantryName + (session.shelfName?.let { " — $it" } ?: ""),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = session.startedAt.toLocalDate().format(dateFormatter),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                StatusBadge(status = session.status)
            }
            if (session.totalItemsInScope > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                val progress = session.checkedItemCount.toFloat() / session.totalItemsInScope.toFloat()
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "${session.checkedItemCount} / ${session.totalItemsInScope} vérifiés" +
                            if (session.discrepancyCount > 0) " · ${session.discrepancyCount} écart(s)" else "",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun StatusBadge(status: InventorySessionStatus) {
    val (label, color) = when (status) {
        InventorySessionStatus.IN_PROGRESS -> "En cours" to MaterialTheme.colorScheme.primary
        InventorySessionStatus.COMPLETED -> "Terminé" to MaterialTheme.colorScheme.secondary
        InventorySessionStatus.CANCELLED -> "Annulé" to MaterialTheme.colorScheme.error
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StartInventoryDialog(
    pantries: List<Pantry>,
    shelves: List<Shelf>,
    selectedPantryId: Long?,
    selectedShelfId: Long?,
    isLoading: Boolean,
    onSelectPantry: (Long) -> Unit,
    onSelectShelf: (Long?) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Démarrer un inventaire") },
        text = {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Pantry dropdown
                    PantryDropdown(
                        pantries = pantries,
                        selectedId = selectedPantryId,
                        onSelect = onSelectPantry
                    )
                    // Optional shelf dropdown
                    if (selectedPantryId != null) {
                        ShelfDropdown(
                            shelves = shelves,
                            selectedId = selectedShelfId,
                            onSelect = onSelectShelf
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = selectedPantryId != null && !isLoading
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Text("Démarrer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Annuler") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PantryDropdown(
    pantries: List<Pantry>,
    selectedId: Long?,
    onSelect: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = pantries.firstOrNull { it.id == selectedId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selected?.name ?: "Choisir un garde-manger",
            onValueChange = {},
            readOnly = true,
            label = { Text("Garde-manger") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            pantries.forEach { pantry ->
                DropdownMenuItem(
                    text = { Text(pantry.name) },
                    onClick = {
                        onSelect(pantry.id)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShelfDropdown(
    shelves: List<Shelf>,
    selectedId: Long?,
    onSelect: (Long?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selected = shelves.firstOrNull { it.id == selectedId }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selected?.name ?: "Tout le garde-manger",
            onValueChange = {},
            readOnly = true,
            label = { Text("Étagère (optionnel)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Tout le garde-manger") },
                onClick = {
                    onSelect(null)
                    expanded = false
                }
            )
            shelves.forEach { shelf ->
                DropdownMenuItem(
                    text = { Text(shelf.name) },
                    onClick = {
                        onSelect(shelf.id)
                        expanded = false
                    }
                )
            }
        }
    }
}
