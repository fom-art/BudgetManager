package com.wf.bm.feature.settings

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SettingsScreen(
        modifier = modifier,
        user = state.user!!,
        preferredCurrency = state.preferredCurrency,
        selectedLanguage = state.language,
        isDarkTheme = state.isDarkTheme,
        changeAvatarPhoto = viewModel::changeAvatarPhoto,
        changePreferredCurrency = viewModel::changePreferredCurrency,
        changeLanguage = viewModel::changeLanguage,
        changeIsDarkTheme = viewModel::changeIsDarkTheme,
        logOut = viewModel::logOut
    )
}
