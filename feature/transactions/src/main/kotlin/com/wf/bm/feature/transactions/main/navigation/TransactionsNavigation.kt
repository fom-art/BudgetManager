package com.wf.bm.feature.transactions.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.core.model.Transaction
import com.wf.bm.feature.transactions.main.TransactionsRoute
import kotlinx.serialization.Serializable

@Serializable
data object TransactionsScreen

fun NavController.navigateToTransactions(navOptions: NavOptions? = null) =
    navigate(TransactionsScreen, navOptions)

fun NavGraphBuilder.transactionsScreen(
    goToTransactionDetails: (Transaction) -> Unit,
) {
    composable<TransactionsScreen> {
        TransactionsRoute(goToTransactionDetails = goToTransactionDetails)
    }
}