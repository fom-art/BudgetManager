package com.wf.bm.feature.settlements.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import com.wf.bm.core.utils.CustomNavType
import com.wf.bm.feature.settlements.details.SettlementDetailsRoute
import com.wf.bm.feature.settlements.details.SettlementDetailsScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class SettlementDetailsScreen(
    val settlement: Settlement
)

fun NavController.navigateToSettlementDetails(navOptions: NavOptions? = null, settlement: Settlement) =
    navigate(SettlementDetailsScreen(settlement), navOptions)

fun NavGraphBuilder.settlementDetailsScreen(
    goBack: () -> Unit
) {
    composable<SettlementDetailsScreen>(
        typeMap = mapOf(typeOf<Settlement>() to CustomNavType(Settlement::class.java, Settlement.serializer()))
    ) {
        val args = it.toRoute<SettlementDetailsScreen>()
        SettlementDetailsRoute(
            settlement = args.settlement,
            goBack = goBack
        )
    }
}