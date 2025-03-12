package com.wf.bm.feature.settlements.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.core.model.Settlement
import com.wf.bm.feature.settlements.history.SettlementsHistoryRoute
import com.wf.bm.feature.settlements.history.SettlementsHistoryScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettlementsHistoryScreen

fun NavController.navigateToSettlementsHistory(navOptions: NavOptions? = null) = navigate(
    SettlementsHistoryScreen, navOptions
)

fun NavGraphBuilder.settlementsHistoryScreen(
    goToSettlementDetails: (NavOptions?, Settlement) -> Unit,
) {
    composable<SettlementsHistoryScreen> {
        SettlementsHistoryRoute(
            goToSettlementDetails = goToSettlementDetails
        )
    }
}