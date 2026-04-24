package com.monstock.app.ui.screens.scanner

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.pdf.PdfDocument
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.monstock.app.R
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.domain.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers

// ─────────────────────────────────────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────────────────────────────────────

data class QrLabelUiState(
    val isLoading: Boolean = true,
    val item: Item? = null,
    val qrBitmap: Bitmap? = null,
    val error: String? = null
)

@HiltViewModel
class QrLabelViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QrLabelUiState())
    val uiState: StateFlow<QrLabelUiState> = _uiState

    fun load(itemId: Long) {
        viewModelScope.launch {
            val item = itemRepository.getItemById(itemId)
            if (item == null) {
                _uiState.update { it.copy(isLoading = false, error = "Article introuvable.") }
                return@launch
            }
            val bitmap = withContext(Dispatchers.Default) { generateQrBitmap(item) }
            _uiState.update { it.copy(isLoading = false, item = item, qrBitmap = bitmap) }
        }
    }

    private fun generateQrBitmap(item: Item): Bitmap {
        val dateFmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        val content = buildString {
            append("MonStock\n")
            append("Nom: ${item.name}\n")
            item.brand?.let { append("Marque: $it\n") }
            item.expiryDate?.let { append("Expiration: ${it.format(dateFmt)}\n") }
            append("Qté: ${item.quantity} ${item.unit.name.lowercase()}\n")
            item.barcode?.let { append("EAN: $it\n") }
        }

        val hints = mapOf(EncodeHintType.MARGIN to 1)
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512, hints)
        val size = bitMatrix.width
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    /** Generates an A4 PDF containing the QR code and item details, writes it to the app cache
     *  and returns the [File]. Must be called from a background dispatcher. */
    fun generatePdfFile(item: Item, bitmap: Bitmap, cacheDir: File): File {
        val dateFmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        // A4 at 72 dpi → 595 × 842 pts
        val pageWidth = 595
        val pageHeight = 842
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val qrSize = 300f
        val left = (pageWidth - qrSize) / 2f
        val top = 80f

        // Draw QR bitmap
        canvas.drawBitmap(bitmap, null, RectF(left, top, left + qrSize, top + qrSize), null)

        // Title
        val titlePaint = Paint().apply {
            textSize = 22f
            textAlign = Paint.Align.CENTER
            color = Color.BLACK
            isFakeBoldText = true
        }
        val bodyPaint = Paint().apply {
            textSize = 16f
            textAlign = Paint.Align.CENTER
            color = Color.DKGRAY
        }
        val centerX = pageWidth / 2f
        var y = top + qrSize + 48f

        canvas.drawText(item.name, centerX, y, titlePaint)
        y += 28f

        item.brand?.let {
            canvas.drawText(it, centerX, y, bodyPaint)
            y += 24f
        }
        item.expiryDate?.let {
            canvas.drawText("Expiration : ${it.format(dateFmt)}", centerX, y, bodyPaint)
            y += 24f
        }
        canvas.drawText("Quantité : ${item.quantity} ${item.unit.name.lowercase()}", centerX, y, bodyPaint)
        y += 24f
        item.barcode?.let {
            canvas.drawText("Code-barres : $it", centerX, y, bodyPaint)
        }

        pdfDocument.finishPage(page)

        val file = File(cacheDir, "qr_label_${item.id}.pdf")
        FileOutputStream(file).use { pdfDocument.writeTo(it) }
        pdfDocument.close()
        return file
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrLabelScreen(
    itemId: Long,
    navController: NavController,
    viewModel: QrLabelViewModel = hiltViewModel()
) {
    LaunchedEffect(itemId) { viewModel.load(itemId) }

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.qr_label_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    if (state.qrBitmap != null) {
                        // Share as PNG
                        IconButton(onClick = {
                            scope.launch(Dispatchers.IO) {
                                val bitmap = state.qrBitmap ?: return@launch
                                val file = File(context.cacheDir, "qr_label_${itemId}.png")
                                FileOutputStream(file).use { out ->
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                                }
                                val uri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    file
                                )
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "image/png"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                context.startActivity(Intent.createChooser(intent, null))
                            }
                        }) {
                            Icon(Icons.Default.Share, contentDescription = stringResource(R.string.action_share))
                        }

                        // Export / share as PDF
                        IconButton(onClick = {
                            scope.launch(Dispatchers.IO) {
                                val bitmap = state.qrBitmap ?: return@launch
                                val item = state.item ?: return@launch
                                val pdfFile = viewModel.generatePdfFile(item, bitmap, context.cacheDir)
                                val uri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.provider",
                                    pdfFile
                                )
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "application/pdf"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                context.startActivity(Intent.createChooser(intent, null))
                            }
                        }) {
                            Icon(Icons.Default.PictureAsPdf, contentDescription = stringResource(R.string.qr_label_export_pdf))
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text(
                    state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(32.dp)
                )
                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(24.dp)
                    ) {
                        state.item?.let { item ->
                            Text(item.name, style = MaterialTheme.typography.titleLarge)
                            item.brand?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                        }

                        state.qrBitmap?.let { bmp ->
                            Image(
                                bitmap = bmp.asImageBitmap(),
                                contentDescription = stringResource(R.string.qr_label_content_description),
                                modifier = Modifier.size(260.dp)
                            )
                        }

                        Text(
                            stringResource(R.string.qr_label_hint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


