package com.monstock.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.monstock.app.R
import com.monstock.app.domain.model.Item
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)

@Composable
fun ItemCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when {
        item.isExpired -> Color(0xFFFFCDD2)        // red-100
        item.isExpiringSoon() -> Color(0xFFFFE0B2) // orange-100
        item.isLowStock -> Color(0xFFFFF9C4)       // yellow-100
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                item.brand?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "${item.quantity} ${item.unit.name.lowercase()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    item.expiryDate?.let { date ->
                        Text(
                            text = "· ${stringResource(R.string.expires)} ${date.format(dateFormatter)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (item.isExpired) Color(0xFFB71C1C) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            if (item.isExpired || item.isExpiringSoon() || item.isLowStock) {
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (item.isExpired) Color(0xFFB71C1C) else Color(0xFFF57C00)
                )
            }
        }
    }
}
