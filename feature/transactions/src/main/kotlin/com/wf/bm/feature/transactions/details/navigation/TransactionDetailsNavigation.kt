package com.wf.bm.feature.transactions.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.utils.CustomNavType
import com.wf.bm.feature.transactions.details.TransactionDetailsRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class TransactionDetailsScreen(val transaction: Transaction)

fun NavController.navigateToTransactionDetails(
    transaction: Transaction,
    navOptions: NavOptions? = null,
) =
    navigate(TransactionDetailsScreen(transaction), navOptions)

fun NavGraphBuilder.transactionDetailsScreen(
    goToUpdateTransaction: (Transaction) -> Unit,
    goBack: () -> Unit,
) {
    composable<TransactionDetailsScreen>(
        typeMap = mapOf(typeOf<Transaction>() to CustomNavType(Transaction::class.java, Transaction.serializer()))

    ) {
        val args = it.toRoute<TransactionDetailsScreen>()
        TransactionDetailsRoute(
            transaction = args.transaction,
            goToUpdateTransaction = goToUpdateTransaction,
            goBack = goBack
        )
    }
}