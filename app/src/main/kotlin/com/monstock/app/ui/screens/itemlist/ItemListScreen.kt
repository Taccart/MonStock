package com.monstock.app.ui.screens.itemlist

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.monstock.app.R
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.ui.components.ConfirmDeleteDialog
import com.monstock.app.ui.components.EmptyState
import com.monstock.app.ui.components.ItemCard
import com.monstock.app.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    shelfId: Long?,
    navController: NavController,
    viewModel: ItemListViewModel = hiltViewModel()
) {
    LaunchedEffect(shelfId) { viewModel.init(shelfId) }

    val items by viewModel.items.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.shelf?.name ?: stringResource(R.string.all_items)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = stringResource(R.string.action_sort))
                    }
                    DropdownMenu(expanded = showSortMenu, onDismissRequest = { showSortMenu = false }) {
                        SortOrder.entries.forEach { order ->
                            DropdownMenuItem(
                                text = { Text(order.label()) },
                                onClick = {
                                    viewModel.onSortOrderChange(order)
                                    showSortMenu = false
                                },
                                leadingIcon = if (uiState.sortOrder == order) ({
                                    Icon(Icons.AutoMirrored.Filled.Sort, null)
                                }) else null
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            val shelfForAdd = shelfId ?: uiState.shelf?.id
            if (shelfForAdd != null) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddEditItem.createAddRoute(shelfForAdd)) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.action_add))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::onSearchQueryChange,
                placeholder = { Text(stringResource(R.string.action_search)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Expiry filter chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExpiryFilter.entries.forEach { filter ->
                    FilterChip(
                        selected = uiState.expiryFilter == filter,
                        onClick = { viewModel.onExpiryFilterChange(filter) },
                        label = { Text(filter.label()) }
                    )
                }
            }

            // Category filter chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.categoryFilter == null,
                    onClick = { viewModel.onCategoryFilterChange(null) },
                    label = { Text(stringResource(R.string.filter_all_categories)) }
                )
                ItemCategory.entries.forEach { cat ->
                    FilterChip(
                        selected = uiState.categoryFilter == cat,
                        onClick = { viewModel.onCategoryFilterChange(cat) },
                        label = { Text(cat.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                }
            }

            if (items.isEmpty()) {
                EmptyState(
                    icon = Icons.Default.Inventory2,
                    message = stringResource(R.string.items_empty)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        ItemCard(
                            item = item,
                            onClick = { navController.navigate(Screen.ItemDetail.createRoute(item.id)) }
                        )
                    }
                }
            }
        }

        uiState.pendingDeleteItem?.let { item ->
            ConfirmDeleteDialog(
                title = stringResource(R.string.item_delete_title),
                message = stringResource(R.string.item_delete_message, item.name),
                onConfirm = viewModel::confirmDelete,
                onDismiss = viewModel::cancelDelete
            )
        }
    }
}

private fun SortOrder.label(): String = when (this) {
    SortOrder.EXPIRY -> "Date d'expiration"
    SortOrder.NAME -> "Nom"
    SortOrder.CATEGORY -> "Catégorie"
    SortOrder.QUANTITY -> "Quantité"
}

private fun ExpiryFilter.label(): String = when (this) {
    ExpiryFilter.ALL -> "Tous"
    ExpiryFilter.EXPIRING_SOON -> "Expire bientôt"
    ExpiryFilter.EXPIRED -> "Expiré"
    ExpiryFilter.OK -> "OK"
}
