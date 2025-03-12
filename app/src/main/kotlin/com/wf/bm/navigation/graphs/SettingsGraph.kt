package com.wf.bm.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.wf.bm.feature.settings.navigation.SettingsScreen
import com.wf.bm.feature.settings.navigation.settingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsGraph

fun NavGraphBuilder.settingsGraph(
    navController: NavController,
) {
    navigation<SettingsGraph>(
        startDestination = SettingsScreen,
    ) {
        settingsScreen()
    }
}