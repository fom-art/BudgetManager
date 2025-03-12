package com.wf.bm.feature.settlements.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.settlements.create.CreateSettlementRoute
import kotlinx.serialization.Serializable

@Serializable
data object CreateSettlementScreen

fun NavController.navigateToCreateSettlement(navOptions: NavOptions? = null) = navigate(CreateSettlementScreen, navOptions)

fun NavGraphBuilder.createSettlementScreen(
    goBack: () -> Unit
) {
    composable<CreateSettlementScreen> {
        CreateSettlementRoute(goBack = goBack)
    }
}