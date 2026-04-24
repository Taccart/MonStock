package com.monstock.app.ui.screens.scanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LayersClear
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.monstock.app.R
import com.monstock.app.ui.navigation.Screen
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarcodeScannerScreen(
    mode: ScannerMode,
    shelfId: Long?,
    navController: NavController,
    viewModel: BarcodeScannerViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Camera permission
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> hasCameraPermission = granted }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    // Handle navigation events
    LaunchedEffect(state.event) {
        when (val event = state.event) {
            is ScannerEvent.ProductFound -> {
                viewModel.consumeEvent()
                navController.navigate(
                    Screen.BarcodeConfirm.createRoute(
                        barcode = event.info.barcode,
                        shelfId = shelfId
                    )
                )
            }
            is ScannerEvent.ProductNotFound -> {
                viewModel.consumeEvent()
                navController.navigate(
                    Screen.AddEditItem.createAddRouteWithBarcode(
                        shelfId = shelfId,
                        barcode = event.barcode
                    )
                )
            }
            is ScannerEvent.RestockTarget -> {
                viewModel.consumeEvent()
                navController.navigate(Screen.RestockScan.createRoute(event.info.barcode))
            }
            is ScannerEvent.InventoryBarcodeScanned -> {
                viewModel.consumeEvent()
                // Pass barcode back to the inventory session screen via savedStateHandle
                navController.previousBackStackEntry?.savedStateHandle?.set("scanned_barcode", event.barcode)
                navController.popBackStack()
            }
            is ScannerEvent.BatchItemScanned -> {
                // Stay on scanner — just show snackbar feedback then clear event
                val label = event.result.info?.name ?: event.result.barcode
                snackbarHostState.showSnackbar(label)
                viewModel.consumeEvent()
            }
            ScannerEvent.BatchScanDone -> {
                viewModel.consumeEvent()
                navController.navigate(Screen.BatchResult.createRoute(shelfId))
            }
            null -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (mode == ScannerMode.RESTOCK)
                            stringResource(R.string.scanner_restock_title)
                        else
                            stringResource(R.string.scanner_title)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    // Flashlight toggle
                    IconButton(onClick = viewModel::toggleFlashlight) {
                        Icon(
                            if (state.isFlashlightOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                            contentDescription = stringResource(R.string.scanner_flashlight)
                        )
                    }
                    // Batch mode toggle (only for ADD_ITEM mode)
                    if (mode == ScannerMode.ADD_ITEM) {
                        IconButton(onClick = viewModel::toggleBatchMode) {
                            Icon(
                                if (state.isBatchMode) Icons.Default.LayersClear else Icons.Default.Layers,
                                contentDescription = stringResource(R.string.scanner_batch_mode)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.6f),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            if (hasCameraPermission) {
                // Camera preview
                CameraPreview(
                    isScanning = state.isScanning,
                    isFlashlightOn = state.isFlashlightOn,
                    onBarcodeDetected = { barcode -> viewModel.onBarcodeDetected(barcode, mode) },
                    modifier = Modifier.fillMaxSize()
                )

                // Scanning overlay / viewfinder
                ScanOverlay(modifier = Modifier.fillMaxSize())

                // Bottom controls
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (state.isBatchMode) {
                        FilterChip(
                            selected = true,
                            onClick = {},
                            label = {
                                Text(
                                    stringResource(R.string.scanner_batch_active, state.processedBarcodes.size),
                                    color = Color.White
                                )
                            }
                        )
                        if (state.processedBarcodes.isNotEmpty()) {
                            Button(onClick = viewModel::finishBatch) {
                                Text(stringResource(R.string.scanner_batch_finish))
                            }
                        }
                    }

                    // Manual entry toggle
                    if (state.showManualEntry) {
                        ManualBarcodeEntry(
                            value = state.manualBarcode,
                            onValueChange = viewModel::onManualBarcodeChange,
                            onSubmit = { viewModel.submitManualBarcode(mode) },
                            onDismiss = viewModel::toggleManualEntry
                        )
                    } else {
                        TextButton(onClick = viewModel::toggleManualEntry) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                stringResource(R.string.scanner_manual_entry),
                                color = Color.White
                            )
                        }
                    }
                }

                // Loading indicator
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            } else {
                // No permission UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        stringResource(R.string.scanner_permission_rationale),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                        Text(stringResource(R.string.scanner_grant_permission))
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Camera preview with ML Kit barcode analysis
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun CameraPreview(
    isScanning: Boolean,
    isFlashlightOn: Boolean,
    onBarcodeDetected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val analysisExecutor = remember { Executors.newSingleThreadExecutor() }

    val previewView = remember { PreviewView(context) }
    var camera by remember { mutableStateOf<androidx.camera.core.Camera?>(null) }

    DisposableEffect(Unit) {
        onDispose { analysisExecutor.shutdown() }
    }

    LaunchedEffect(isFlashlightOn) {
        camera?.cameraControl?.enableTorch(isFlashlightOn)
    }

    LaunchedEffect(isScanning) {
        // Nothing to do here; the analyzer checks isScanning internally via closure
    }

    AndroidView(
        factory = { previewView },
        modifier = modifier,
        update = { view ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = view.surfaceProvider
                }

                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                        Barcode.FORMAT_EAN_13,
                        Barcode.FORMAT_EAN_8,
                        Barcode.FORMAT_UPC_A,
                        Barcode.FORMAT_UPC_E,
                        Barcode.FORMAT_QR_CODE
                    )
                    .build()
                val scanner = BarcodeScanning.getClient(options)

                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { imageAnalysis ->
                        imageAnalysis.setAnalyzer(analysisExecutor) { imageProxy ->
                            if (!isScanning) {
                                imageProxy.close()
                                return@setAnalyzer
                            }
                            @Suppress("UnsafeOptInUsageError")
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )
                                scanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        barcodes.firstOrNull()?.rawValue?.let(onBarcodeDetected)
                                    }
                                    .addOnCompleteListener { imageProxy.close() }
                            } else {
                                imageProxy.close()
                            }
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analysis
                    )
                } catch (_: Exception) { /* handle bind failure */ }

            }, ContextCompat.getMainExecutor(context))
        }
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Scan-frame overlay
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ScanOverlay(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .aspectRatio(1.6f)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Manual barcode entry field
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ManualBarcodeEntry(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(stringResource(R.string.scanner_manual_barcode_hint), color = Color.White) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onSubmit() }),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onSubmit) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
        TextButton(onClick = onDismiss) {
            Text(stringResource(R.string.action_cancel), color = Color.White)
        }
    }
}
