package com.monstock.app.ui.screens.scanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.monstock.app.R
import com.monstock.app.data.model.ItemCategory
import com.monstock.app.data.model.ItemUnit
import com.monstock.app.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeConfirmScreen(
    barcode: String,
    shelfId: Long?,
    navController: NavController,
    viewModel: BarcodeConfirmViewModel = hiltViewModel()
) {
    LaunchedEffect(barcode) { viewModel.load(barcode) }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Navigate to AddEdit when confirmed
    LaunchedEffect(state.confirmed) {
        if (state.confirmed) {
            navController.navigate(
                Screen.AddEditItem.createAddRouteWithBarcode(
                    shelfId = shelfId,
                    barcode = barcode,
                    name = state.name,
                    brand = state.brand,
                    category = state.category.name,
                    unit = state.unit.name,
                    imageUrl = state.imageUrl
                )
            ) {
                // Pop both the confirm screen and the scanner from the back-stack
                popUpTo(Screen.BarcodeConfirm.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.scanner_confirm_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (state.isUnknownProduct) {
                Text(
                    stringResource(R.string.scanner_product_not_found, barcode),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Product image
            state.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            // Barcode (read-only)
            OutlinedTextField(
                value = barcode,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.field_barcode)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Name
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text(stringResource(R.string.field_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Brand
            OutlinedTextField(
                value = state.brand,
                onValueChange = viewModel::onBrandChange,
                label = { Text(stringResource(R.string.field_brand)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Category
            SimpleDropdown(
                label = stringResource(R.string.field_category),
                selected = state.category,
                options = ItemCategory.entries,
                displayName = { it.name.lowercase().replaceFirstChar { c -> c.uppercase() } },
                onSelect = viewModel::onCategoryChange,
                modifier = Modifier.fillMaxWidth()
            )

            // Unit
            SimpleDropdown(
                label = stringResource(R.string.field_unit),
                selected = state.unit,
                options = ItemUnit.entries,
                displayName = { it.name.lowercase() },
                onSelect = viewModel::onUnitChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) { Text(stringResource(R.string.action_cancel)) }

                Button(
                    onClick = { viewModel.confirm() },
                    modifier = Modifier.weight(1f),
                    enabled = state.name.isNotBlank()
                ) { Text(stringResource(R.string.scanner_confirm_use)) }
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> SimpleDropdown(
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
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(displayName(opt)) },
                    onClick = { onSelect(opt); expanded = false }
                )
            }
        }
    }
}
