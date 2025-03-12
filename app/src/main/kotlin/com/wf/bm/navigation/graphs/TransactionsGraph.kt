package com.wf.bm.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.wf.bm.core.model.Transaction
import com.wf.bm.feature.transactions.details.navigation.navigateToTransactionDetails
import com.wf.bm.feature.transactions.details.navigation.transactionDetailsScreen
import com.wf.bm.feature.transactions.edit.navigation.editTransactionScreen
import com.wf.bm.feature.transactions.edit.navigation.navigateToEditTransaction
import com.wf.bm.feature.transactions.main.navigation.TransactionsScreen
import com.wf.bm.feature.transactions.main.navigation.transactionsScreen
import kotlinx.serialization.Serializable

@Serializable
data object TransactionsGraph

fun NavGraphBuilder.transactionsGraph(
    navController: NavController,
) {
    navigation<TransactionsGraph>(
        startDestination = TransactionsScreen,
    ) {
        transactionsScreen(
            goToTransactionDetails = navController::navigateToTransactionDetails
        )
        editTransactionScreen(
            goBack = navController::popBackStack
        )
        transactionDetailsScreen(
            goToUpdateTransaction = navController::navigateToEditTransaction,
            goBack = navController::popBackStack
        )
    }
}