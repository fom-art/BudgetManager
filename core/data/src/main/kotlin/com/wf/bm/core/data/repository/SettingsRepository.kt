package com.wf.bm.core.data.repository

import com.wf.bm.core.model.Currency
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getPreferredCurrency(): Flow<Currency>
    suspend fun getLanguage(): Flow<String>
    suspend fun getIsDarkTheme(): Flow<Boolean>
    suspend fun setPreferredCurrency(currency: Currency)
    suspend fun setLanguage(language: String)
    suspend fun setIsDarkTheme(isDarkTheme: Boolean)
}