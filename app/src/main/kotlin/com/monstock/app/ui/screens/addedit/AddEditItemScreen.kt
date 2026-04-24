package com.monstock.app.ui.screens.addedit

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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.monstock.app.R
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditItemScreen(
    itemId: Long?,
    shelfId: Long?,
    prefillBarcode: String? = null,
    prefillName: String? = null,
    prefillBrand: String? = null,
    prefillCategory: String? = null,
    prefillUnit: String? = null,
    prefillImageUrl: String? = null,
    navController: NavController,
    viewModel: AddEditItemViewModel = hiltViewModel()
) {
    LaunchedEffect(itemId, shelfId) {
        viewModel.init(
            itemId, shelfId,
            prefillBarcode, prefillName, prefillBrand,
            prefillCategory, prefillUnit, prefillImageUrl
        )
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (state.isEditMode) stringResource(R.string.item_edit_title)
                        else stringResource(R.string.item_add_title)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // --- Name ---
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text(stringResource(R.string.field_name) + " *") },
                isError = state.nameError,
                supportingText = if (state.nameError) ({
                    Text(stringResource(R.string.error_field_required))
                }) else null,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            // --- Brand ---
            OutlinedTextField(
                value = state.brand,
                onValueChange = viewModel::onBrandChange,
                label = { Text(stringResource(R.string.field_brand)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // --- Category ---
            EnumDropdown(
                label = stringResource(R.string.field_category),
                selected = state.category,
                options = ItemCategory.entries,
                displayName = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
                onSelect = viewModel::onCategoryChange,
                modifier = Modifier.fillMaxWidth()
            )

            // --- Quantity & Unit ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = state.quantity,
                    onValueChange = viewModel::onQuantityChange,
                    label = { Text(stringResource(R.string.field_quantity)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
                EnumDropdown(
                    label = stringResource(R.string.field_unit),
                    selected = state.unit,
                    options = ItemUnit.entries,
                    displayName = { it.name.lowercase() },
                    onSelect = viewModel::onUnitChange,
                    modifier = Modifier.weight(1f)
                )
            }

            // --- Purchase date ---
            DateField(
                label = stringResource(R.string.field_purchase_date),
                date = state.purchaseDate,
                onDateSelected = viewModel::onPurchaseDateChange
            )

            // --- Expiry date ---
            DateField(
                label = stringResource(R.string.field_expiry_date),
                date = state.expiryDate,
                onDateSelected = viewModel::onExpiryDateChange
            )

            // --- Min stock threshold ---
            OutlinedTextField(
                value = state.minimumStockThreshold,
                onValueChange = viewModel::onMinThresholdChange,
                label = { Text(stringResource(R.string.field_min_threshold)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // --- Purchase price per unit ---
            OutlinedTextField(
                value = state.purchasePrice,
                onValueChange = viewModel::onPurchasePriceChange,
                label = { Text(stringResource(R.string.field_purchase_price)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // --- Shelf picker ---
            if (state.pantries.isNotEmpty()) {
                Text(stringResource(R.string.field_shelf), style = MaterialTheme.typography.labelLarge)

                EnumDropdown(
                    label = stringResource(R.string.field_pantry),
                    selected = state.pantries.find { it.id == state.selectedPantryId },
                    options = state.pantries,
                    displayName = { it?.name ?: "" },
                    onSelect = { it?.let { p -> viewModel.loadShelvesForPantry(p.id) } },
                    modifier = Modifier.fillMaxWidth()
                )

                if (state.shelves.isNotEmpty()) {
                    EnumDropdown(
                        label = stringResource(R.string.field_shelf) + if (state.shelfError) " *" else "",
                        selected = state.shelves.find { it.id == state.selectedShelfId },
                        options = state.shelves,
                        displayName = { it?.name ?: "" },
                        onSelect = { it?.let { s -> viewModel.onShelfSelected(s.id) } },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (state.shelfError) {
                        Text(
                            text = stringResource(R.string.error_field_required),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // --- Barcode ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = state.barcode,
                    onValueChange = viewModel::onBarcodeChange,
                    label = { Text(stringResource(R.string.field_barcode)) },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                androidx.compose.material3.IconButton(
                    onClick = {
                        navController.navigate(
                            com.monstock.app.ui.navigation.Screen.BarcodeScanner.createRoute(
                                mode = com.monstock.app.ui.screens.scanner.ScannerMode.ADD_ITEM,
                                shelfId = state.selectedShelfId
                            )
                        )
                    }
                ) {
                    androidx.compose.material3.Icon(
                        androidx.compose.material.icons.Icons.Default.QrCodeScanner,
                        contentDescription = stringResource(R.string.scanner_title)
                    )
                }
            }

            // --- Notes ---
            OutlinedTextField(
                value = state.notes,
                onValueChange = viewModel::onNotesChange,
                label = { Text(stringResource(R.string.field_notes)) },
                maxLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { viewModel.save(itemId) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.action_save))
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> EnumDropdown(
    label: String,
    selected: T,
    options: List<T>,
    displayName: (T) -> String,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = displayName(selected),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(displayName(option)) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate?) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = date?.format(dateFormatter) ?: "",
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Default.CalendarMonth, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showPicker) {
        val pickerState = rememberDatePickerState(
            initialSelectedDateMillis = date
                ?.atStartOfDay(ZoneId.of("UTC"))
                ?.toInstant()
                ?.toEpochMilli()
        )
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = pickerState.selectedDateMillis
                    onDateSelected(
                        millis?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                        }
                    )
                    showPicker = false
                }) { Text(stringResource(R.string.action_confirm)) }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }
}
