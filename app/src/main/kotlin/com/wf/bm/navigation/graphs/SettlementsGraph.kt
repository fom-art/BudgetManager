package com.wf.bm.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import com.wf.bm.feature.settlements.create.navigation.createSettlementScreen
import com.wf.bm.feature.settlements.create.navigation.navigateToCreateSettlement
import com.wf.bm.feature.settlements.details.navigation.navigateToSettlementDetails
import com.wf.bm.feature.settlements.details.navigation.settlementDetailsScreen
import com.wf.bm.feature.settlements.history.navigation.SettlementsHistoryScreen
import com.wf.bm.feature.settlements.history.navigation.navigateToSettlementsHistory
import com.wf.bm.feature.settlements.history.navigation.settlementsHistoryScreen
import com.wf.bm.feature.settlements.main.navigation.SettlementsScreen
import com.wf.bm.feature.settlements.main.navigation.settlementsScreen
import com.wf.bm.feature.settlements.notifications.navigation.navigateToSettlementsNotifications
import com.wf.bm.feature.settlements.notifications.navigation.settlementsNotificationsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettlementsGraph

fun NavGraphBuilder.settlementsGraph(
    navController: NavController,
) {
    navigation<SettlementsGraph>(
        startDestination = SettlementsScreen,
    ) {
        settlementsScreen(
            goToNotifications = navController::navigateToSettlementsNotifications,
            goToSettlementDetails = navController::navigateToSettlementDetails,
            goToSettlementsHistory = navController::navigateToSettlementsHistory,
            goToCreateSettlement = navController::navigateToCreateSettlement
        )
        settlementDetailsScreen(
            goBack = navController::popBackStack
        )
        settlementsHistoryScreen(
            goToSettlementDetails = navController::navigateToSettlementDetails
        )
        createSettlementScreen(
            goBack = navController::popBackStack
        )
        settlementsNotificationsScreen(
            goBack = navController::popBackStack
        )
    }
}