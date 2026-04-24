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
 * Periodic worker that fires a daily digest notification summarising:
 *  - how many items are expiring soon (within the configured alert window)
 *  - how many items are below their minimum stock threshold
 *
 * The worker is scheduled with a 24-hour repeat interval.
 * If the user has disabled daily digest in preferences this worker exits early.
 */
@HiltWorker
class DailyDigestWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val itemRepository: ItemRepository,
    private val notificationHelper: NotificationHelper,
    private val notificationPreferences: NotificationPreferences
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val digestEnabled = notificationPreferences.dailyDigestEnabled.first()
        if (!digestEnabled) return Result.success()

        val expiryAlertDays = notificationPreferences.expiryAlertDays.first().toLong()
        val lowStockEnabled = notificationPreferences.lowStockEnabled.first()

        val expiringCount = itemRepository.getItemsExpiringSoon(expiryAlertDays).first().size +
                itemRepository.getExpiredItems().first().size

        val lowStockCount = if (lowStockEnabled) {
            itemRepository.getLowStockItems().first().size
        } else {
            0
        }

        notificationHelper.postDailyDigestNotification(
            expiringCount = expiringCount,
            lowStockCount = lowStockCount
        )

        return Result.success()
    }
}
