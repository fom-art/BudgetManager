package com.wf.bm.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.SettingsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.combine

class SettingsViewModel(
    private val authenticationManager: AuthenticationManager,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settingsState = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _settingsState.asStateFlow()

    init {
        _settingsState.update { it.copy(user = authenticationManager.user) }
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            combine(
                settingsRepository.getPreferredCurrency(),
                settingsRepository.getLanguage(),
                settingsRepository.getIsDarkTheme()
            ) { currency, language, isDarkTheme ->
                _settingsState.update {
                    it.copy(
                        preferredCurrency = currency,
                        language = language,
                        isDarkTheme = isDarkTheme,
                    )
                }
            }.collect()
        }
    }

    fun changeAvatarPhoto() {
        // Add logic for avatar photo change
    }

    fun changePreferredCurrency(newCurrency: Currency) {
        viewModelScope.launch { settingsRepository.setPreferredCurrency(newCurrency) }
    }

    fun changeLanguage(newLanguage: String) {
        viewModelScope.launch { settingsRepository.setLanguage(newLanguage) }
    }

    fun changeIsDarkTheme(isDark: Boolean) {
        viewModelScope.launch { settingsRepository.setIsDarkTheme(isDark) }
    }

    fun logOut() {
        authenticationManager.logOut()
    }

    data class SettingsState(
        val preferredCurrency: Currency = Currency.USD,
        val language: String = "English",
        val isDarkTheme: Boolean = true,
        val user: User? = null
    )
}
