package com.monstock.app.ui.screens.itemdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.monstock.app.R
import com.monstock.app.data.model.ConsumptionEventType
import com.monstock.app.domain.model.Item
import com.monstock.app.ui.components.ConfirmDeleteDialog
import com.monstock.app.ui.navigation.Screen
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: Long,
    navController: NavController,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(itemId) { viewModel.loadItem(itemId) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Navigate back when deleted
    LaunchedEffect(uiState.deleted) {
        if (uiState.deleted) navController.popBackStack()
    }

    val item = uiState.item

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    item?.let {
                        IconButton(onClick = { navController.navigate(Screen.QrLabel.createRoute(item.id)) }) {
                            Icon(Icons.Default.QrCode, contentDescription = stringResource(R.string.qr_label_title))
                        }
                        IconButton(onClick = { navController.navigate(Screen.AddEditItem.createEditRoute(item.id)) }) {
                            Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.action_edit))
                        }
                        IconButton(onClick = viewModel::requestDelete) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.action_delete))
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (item != null) {
            ItemDetailContent(
                item = item,
                shelfName = uiState.shelf?.name,
                onLogConsumed = viewModel::requestLogConsumed,
                onLogWasted = viewModel::requestLogWasted,
                modifier = Modifier.padding(innerPadding)
            )
        }

        if (uiState.showDeleteDialog) {
            ConfirmDeleteDialog(
                title = stringResource(R.string.item_delete_title),
                message = stringResource(R.string.item_delete_message, item?.name ?: ""),
                onConfirm = viewModel::confirmDelete,
                onDismiss = viewModel::cancelDelete
            )
        }

        // Phase 4 — log consumption / waste dialog
        if (uiState.showLogDialog) {
            LogConsumptionDialog(
                eventType = uiState.logEventType,
                quantity = uiState.logQuantity,
                quantityError = uiState.logQuantityError,
                onQuantityChange = viewModel::onLogQuantityChange,
                onConfirm = viewModel::confirmLog,
                onDismiss = viewModel::cancelLog
            )
        }
    }
}

@Composable
private fun LogConsumptionDialog(
    eventType: ConsumptionEventType,
    quantity: String,
    quantityError: Boolean,
    onQuantityChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val titleRes = if (eventType == ConsumptionEventType.CONSUMED)
        R.string.stats_log_consumed_title else R.string.stats_log_wasted_title

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(titleRes)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.stats_log_qty_label))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = onQuantityChange,
                    isError = quantityError,
                    supportingText = if (quantityError) ({ Text(stringResource(R.string.error_field_required)) }) else null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) { Text(stringResource(R.string.action_confirm)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        }
    )
}

@Composable
private fun ItemDetailContent(
    item: Item,
    shelfName: String?,
    onLogConsumed: () -> Unit,
    onLogWasted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Status banner
        if (item.isExpired || item.isExpiringSoon() || item.isLowStock) {
            val (color, label) = when {
                item.isExpired -> Color(0xFFFFCDD2) to stringResource(R.string.status_expired)
                item.isExpiringSoon() -> Color(0xFFFFE0B2) to stringResource(R.string.status_expiring_soon)
                else -> Color(0xFFFFF9C4) to stringResource(R.string.status_low_stock)
            }
            Card(colors = CardDefaults.cardColors(containerColor = color)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = null)
                    Text(label, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Main info card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DetailRow(label = stringResource(R.string.field_name), value = item.name)
                item.brand?.let { DetailRow(stringResource(R.string.field_brand), it) }
                DetailRow(stringResource(R.string.field_category), item.category.name.lowercase().replaceFirstChar { it.uppercase() })
                HorizontalDivider()
                DetailRow(stringResource(R.string.field_quantity), "${item.quantity} ${item.unit.name.lowercase()}")
                item.minimumStockThreshold?.let {
                    DetailRow(stringResource(R.string.field_min_threshold), "${it} ${item.unit.name.lowercase()}")
                }
                item.purchasePricePerUnit?.let {
                    DetailRow(stringResource(R.string.field_purchase_price), "%.2f €".format(it))
                }
                HorizontalDivider()
                item.purchaseDate?.let {
                    DetailRow(stringResource(R.string.field_purchase_date), it.format(dateFormatter))
                }
                item.expiryDate?.let {
                    DetailRow(
                        label = stringResource(R.string.field_expiry_date),
                        value = it.format(dateFormatter),
                        valueColor = when {
                            item.isExpired -> Color(0xFFB71C1C)
                            item.isExpiringSoon() -> Color(0xFFF57C00)
                            else -> null
                        }
                    )
                }
                shelfName?.let { DetailRow(stringResource(R.string.field_shelf), it) }
                item.barcode?.let { DetailRow(stringResource(R.string.field_barcode), it) }
                item.notes?.let { DetailRow(stringResource(R.string.field_notes), it) }
            }
        }

        // Phase 4 — log consumption / waste buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onLogConsumed,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(stringResource(R.string.stats_action_consumed))
            }
            Button(
                onClick = onLogWasted,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(stringResource(R.string.stats_action_wasted))
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    valueColor: Color? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.45f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = valueColor ?: MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.55f)
        )
    }
}
