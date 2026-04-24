package com.monstock.app.notification

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.monstock.app.notification.worker.DailyDigestWorker
import com.monstock.app.notification.worker.StockAlertWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Schedules the Phase 3 periodic WorkManager tasks:
 *  - [StockAlertWorker]  every 12 hours (expiry + low-stock alerts)
 *  - [DailyDigestWorker] every 24 hours (morning digest)
 *
 * Workers are enqueued with [ExistingPeriodicWorkPolicy.KEEP] so calling
 * [scheduleAll] multiple times (e.g. on every app launch) is safe and
 * does not reset the existing schedule.
 */
@Singleton
class NotificationScheduler @Inject constructor(
    private val context: Context
) {

    private val workManager get() = WorkManager.getInstance(context)

    companion object {
        const val WORK_STOCK_ALERT = "work_stock_alert"
        const val WORK_DAILY_DIGEST = "work_daily_digest"
    }

    fun scheduleAll() {
        scheduleStockAlert()
        scheduleDailyDigest()
    }

    private fun scheduleStockAlert() {
        val request = PeriodicWorkRequestBuilder<StockAlertWorker>(
            repeatInterval = 12,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(false)
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_STOCK_ALERT,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    private fun scheduleDailyDigest() {
        val request = PeriodicWorkRequestBuilder<DailyDigestWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_DAILY_DIGEST,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    /** Cancel all notification workers (e.g. when the user disables all notifications). */
    fun cancelAll() {
        workManager.cancelUniqueWork(WORK_STOCK_ALERT)
        workManager.cancelUniqueWork(WORK_DAILY_DIGEST)
    }
}
