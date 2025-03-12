package com.wf.bm.feature.settlements.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.core.model.Settlement
import com.wf.bm.feature.settlements.history.navigation.SettlementsHistoryScreen
import com.wf.bm.feature.settlements.main.SettlementsRoute
import kotlinx.serialization.Serializable

@Serializable
data object SettlementsScreen

fun NavController.navigateToSettlements(navOptions: NavOptions? = null) = navigate(
    SettlementsHistoryScreen, navOptions
)

fun NavGraphBuilder.settlementsScreen(
    goToNotifications: () -> Unit,
    goToSettlementDetails: (NavOptions?, Settlement) -> Unit,
    goToSettlementsHistory: () -> Unit,
    goToCreateSettlement: () -> Unit,
) {
    composable<SettlementsScreen> {
        SettlementsRoute(
            goToNotifications = goToNotifications,
            goToSettlementDetails = goToSettlementDetails,
            goToSettlementsHistory = goToSettlementsHistory,
            goToCreateSettlement = goToCreateSettlement
        )
    }
}