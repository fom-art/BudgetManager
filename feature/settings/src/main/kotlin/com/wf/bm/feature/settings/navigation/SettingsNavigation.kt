package com.wf.bm.feature.settings.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.settings.SettingsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettingsScreen

fun NavController.navigateToSettings(navOptions: NavOptions? = null) = navigate(SettingsScreen, navOptions)

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsScreen> {
        SettingsRoute()
    }
}