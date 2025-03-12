package com.wf.bm.core.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.wf.bm.core.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    private val Context.dataStore by preferencesDataStore(name = "user_settings")

    companion object {
        private val PREFERRED_CURRENCY_KEY = stringPreferencesKey("preferred_currency")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    }

    override suspend fun getPreferredCurrency(): Flow<Currency> {
        return context.dataStore.data.map { preferences ->
            val currencyName = preferences[PREFERRED_CURRENCY_KEY] ?: Currency.USD.name
            Currency.valueOf(currencyName)
        }
    }

    override suspend fun getLanguage(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: "English"
        }
    }

    override suspend fun getIsDarkTheme(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[IS_DARK_THEME_KEY] ?: true // Default to dark theme enabled
        }
    }

    override suspend  fun setPreferredCurrency(currency: Currency) {
        context.dataStore.edit { preferences ->
            preferences[PREFERRED_CURRENCY_KEY] = currency.name
        }
    }

    override suspend fun setLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    override suspend  fun setIsDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME_KEY] = isDarkTheme
        }
    }
}
