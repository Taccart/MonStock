package com.monstock.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.monstock.app.R
import com.monstock.app.ui.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(
    private val context: Context
) {

    companion object {
        const val CHANNEL_EXPIRY = "expiry_alert"
        const val CHANNEL_LOW_STOCK = "low_stock_alert"
        const val CHANNEL_DAILY_DIGEST = "daily_digest"

        const val NOTIF_ID_EXPIRY = 1001
        const val NOTIF_ID_LOW_STOCK = 1002
        const val NOTIF_ID_DAILY_DIGEST = 1003
    }

    /** Must be called once at application start to register all notification channels. */
    fun createNotificationChannels() {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_EXPIRY,
                context.getString(R.string.notif_channel_expiry_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notif_channel_expiry_desc)
            }
        )

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_LOW_STOCK,
                context.getString(R.string.notif_channel_low_stock_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notif_channel_low_stock_desc)
            }
        )

        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_DAILY_DIGEST,
                context.getString(R.string.notif_channel_digest_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = context.getString(R.string.notif_channel_digest_desc)
            }
        )
    }

    // -------------------------------------------------------------------------
    // Expiry alert
    // -------------------------------------------------------------------------

    /**
     * Posts a notification listing items that expire within the configured window.
     * @param itemNames names of items expiring soon or already expired
     * @param expiredCount number of already-expired items
     */
    fun postExpiryNotification(itemNames: List<String>, expiredCount: Int) {
        if (itemNames.isEmpty()) return

        val title = if (expiredCount > 0) {
            context.getString(R.string.notif_expiry_title_expired, expiredCount)
        } else {
            context.getString(R.string.notif_expiry_title_soon, itemNames.size)
        }

        val body = itemNames.take(5).joinToString(", ").let { preview ->
            if (itemNames.size > 5) "$preview…" else preview
        }

        val notification = buildNotification(CHANNEL_EXPIRY, title, body)
        NotificationManagerCompat.from(context).notify(NOTIF_ID_EXPIRY, notification)
    }

    // -------------------------------------------------------------------------
    // Low-stock alert
    // -------------------------------------------------------------------------

    /**
     * Posts a notification listing items below their minimum stock threshold.
     * @param itemNames names of low-stock items
     */
    fun postLowStockNotification(itemNames: List<String>) {
        if (itemNames.isEmpty()) return

        val title = context.getString(R.string.notif_low_stock_title, itemNames.size)
        val body = itemNames.take(5).joinToString(", ").let { preview ->
            if (itemNames.size > 5) "$preview…" else preview
        }

        val notification = buildNotification(CHANNEL_LOW_STOCK, title, body)
        NotificationManagerCompat.from(context).notify(NOTIF_ID_LOW_STOCK, notification)
    }

    // -------------------------------------------------------------------------
    // Daily digest
    // -------------------------------------------------------------------------

    /**
     * Posts a daily summary notification.
     * @param expiringCount items expiring within the configured alert window
     * @param lowStockCount items below their minimum threshold
     */
    fun postDailyDigestNotification(expiringCount: Int, lowStockCount: Int) {
        if (expiringCount == 0 && lowStockCount == 0) return

        val title = context.getString(R.string.notif_digest_title)
        val body = buildString {
            if (expiringCount > 0) append(context.getString(R.string.notif_digest_expiring, expiringCount))
            if (expiringCount > 0 && lowStockCount > 0) append(" · ")
            if (lowStockCount > 0) append(context.getString(R.string.notif_digest_low_stock, lowStockCount))
        }

        val notification = buildNotification(CHANNEL_DAILY_DIGEST, title, body)
        NotificationManagerCompat.from(context).notify(NOTIF_ID_DAILY_DIGEST, notification)
    }

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    private fun buildNotification(
        channel: String,
        title: String,
        body: String
    ) = NotificationCompat.Builder(context, channel)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(title)
        .setContentText(body)
        .setStyle(NotificationCompat.BigTextStyle().bigText(body))
        .setContentIntent(mainActivityPendingIntent())
        .setAutoCancel(true)
        .build()

    private fun mainActivityPendingIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
