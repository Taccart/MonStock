package com.monstock.app.notification.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.monstock.app.data.repository.ItemRepository
import com.monstock.app.notification.NotificationHelper
import com.monstock.app.notification.NotificationPreferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

/**
 * Periodic worker (every ~12 hours) that:
 *  1. Checks items expiring within the configured alert window and posts an expiry notification.
 *  2. Checks low-stock items (if enabled) and posts a low-stock notification.
 */
@HiltWorker
class StockAlertWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val itemRepository: ItemRepository,
    private val notificationHelper: NotificationHelper,
    private val notificationPreferences: NotificationPreferences
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val expiryAlertDays = notificationPreferences.expiryAlertDays.first().toLong()
        val lowStockEnabled = notificationPreferences.lowStockEnabled.first()

        // --- Expiry alert ---
        val expiringItems = itemRepository.getItemsExpiringSoon(expiryAlertDays).first()
        val expiredItems = itemRepository.getExpiredItems().first()

        val allAlertItems = (expiredItems + expiringItems)
            .distinctBy { it.id }
            .sortedBy { it.expiryDate }

        notificationHelper.postExpiryNotification(
            itemNames = allAlertItems.map { it.name },
            expiredCount = expiredItems.size
        )

        // --- Low stock alert ---
        if (lowStockEnabled) {
            val lowStockItems = itemRepository.getLowStockItems().first()
            notificationHelper.postLowStockNotification(
                itemNames = lowStockItems.map { it.name }
            )
        }

        return Result.success()
    }
}
