package com.monstock.app.notification

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Persists user preferences related to Phase 3 notifications using DataStore.
 *
 * Defaults:
 *  - expiryAlertDays      = 3  (fire alert this many days before expiry)
 *  - lowStockEnabled      = true
 *  - dailyDigestEnabled   = false
 *  - dailyDigestHour      = 8  (08:00 local time)
 */
@Singleton
class NotificationPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val KEY_EXPIRY_ALERT_DAYS = intPreferencesKey("expiry_alert_days")
        val KEY_LOW_STOCK_ENABLED = booleanPreferencesKey("low_stock_enabled")
        val KEY_DAILY_DIGEST_ENABLED = booleanPreferencesKey("daily_digest_enabled")
        val KEY_DAILY_DIGEST_HOUR = intPreferencesKey("daily_digest_hour")

        const val DEFAULT_EXPIRY_ALERT_DAYS = 3
        const val DEFAULT_DAILY_DIGEST_HOUR = 8
    }

    val expiryAlertDays: Flow<Int> = dataStore.data.map { prefs ->
        prefs[KEY_EXPIRY_ALERT_DAYS] ?: DEFAULT_EXPIRY_ALERT_DAYS
    }

    val lowStockEnabled: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[KEY_LOW_STOCK_ENABLED] ?: true
    }

    val dailyDigestEnabled: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[KEY_DAILY_DIGEST_ENABLED] ?: false
    }

    val dailyDigestHour: Flow<Int> = dataStore.data.map { prefs ->
        prefs[KEY_DAILY_DIGEST_HOUR] ?: DEFAULT_DAILY_DIGEST_HOUR
    }

    suspend fun setExpiryAlertDays(days: Int) {
        dataStore.edit { it[KEY_EXPIRY_ALERT_DAYS] = days.coerceIn(1, 30) }
    }

    suspend fun setLowStockEnabled(enabled: Boolean) {
        dataStore.edit { it[KEY_LOW_STOCK_ENABLED] = enabled }
    }

    suspend fun setDailyDigestEnabled(enabled: Boolean) {
        dataStore.edit { it[KEY_DAILY_DIGEST_ENABLED] = enabled }
    }

    suspend fun setDailyDigestHour(hour: Int) {
        dataStore.edit { it[KEY_DAILY_DIGEST_HOUR] = hour.coerceIn(0, 23) }
    }
}
