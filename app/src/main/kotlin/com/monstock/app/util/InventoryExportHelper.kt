package com.monstock.app.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.monstock.app.domain.model.InventoryEntry
import com.monstock.app.domain.model.InventorySession
import java.io.File
import java.time.format.DateTimeFormatter

object InventoryExportHelper {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    /**
     * Builds a CSV file for [session] and its [entries], then launches the system share sheet.
     */
    fun exportAndShare(context: Context, session: InventorySession, entries: List<InventoryEntry>) {
        val csv = buildCsv(session, entries)
        val fileName = "inventaire_${session.pantryName.replace(' ', '_')}_${session.startedAt.toLocalDate().format(dateFormatter)}.csv"
        val file = File(context.cacheDir, fileName)
        file.writeText(csv, Charsets.UTF_8)

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Rapport d'inventaire — ${session.pantryName}")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Exporter le rapport"))
    }

    private fun buildCsv(session: InventorySession, entries: List<InventoryEntry>): String {
        val sb = StringBuilder()
        // Header
        sb.appendLine("Garde-manger;Étagère;Article;Qté enregistrée;Unité;Exp. enregistrée;Qté comptée;Exp. corrigée;Vérifié;Écart;Manquant")
        entries.forEach { entry ->
            val hasQtyDiscrepancy = entry.countedQuantity != null && entry.countedQuantity != entry.recordedQuantity
            val hasExpDiscrepancy = entry.correctedExpiryDate != null && entry.correctedExpiryDate != entry.recordedExpiryDate
            val hasDiscrepancy = hasQtyDiscrepancy || hasExpDiscrepancy
            sb.appendLine(
                listOf(
                    session.pantryName,
                    entry.shelfName,
                    entry.itemName,
                    entry.recordedQuantity.toString(),
                    entry.unit.name.lowercase(),
                    entry.recordedExpiryDate?.format(dateFormatter) ?: "",
                    entry.countedQuantity?.toString() ?: "",
                    entry.correctedExpiryDate?.format(dateFormatter) ?: "",
                    if (entry.isChecked) "oui" else "non",
                    if (hasDiscrepancy) "oui" else "non",
                    if (!entry.isChecked) "possible" else "non"
                ).joinToString(";")
            )
        }
        return sb.toString()
    }
}
