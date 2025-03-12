package com.wf.bm.feature.settlements.notifications.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.settlements.notifications.SettlementsNotificationsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettlementsNotificationsScreen

fun NavController.navigateToSettlementsNotifications(navOptions: NavOptions? = null) = navigate(SettlementsNotificationsScreen, navOptions)

fun NavGraphBuilder.settlementsNotificationsScreen(    goBack: () -> Unit
) {
    composable<SettlementsNotificationsScreen> {
        SettlementsNotificationsRoute(goBack = goBack)
    }
}