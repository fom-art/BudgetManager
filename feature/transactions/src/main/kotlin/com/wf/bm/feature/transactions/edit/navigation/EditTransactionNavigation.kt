package com.wf.bm.feature.transactions.edit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.utils.CustomNavType
import com.wf.bm.feature.transactions.edit.EditTransactionRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class EditTransactionScreen(
    val transaction: Transaction
)

fun NavController.navigateToEditTransaction(
    transaction: Transaction,
    navOptions: NavOptions? = null,
) = navigate(
    EditTransactionScreen(transaction), navOptions
)

fun NavGraphBuilder.editTransactionScreen(
    goBack: () -> Unit,
) {
    composable<EditTransactionScreen>(
        typeMap = mapOf(typeOf<Transaction>() to CustomNavType(Transaction::class.java, Transaction.serializer()))
    ) {
        val args = it.toRoute<EditTransactionScreen>()
        EditTransactionRoute(transaction = args.transaction, goBack = goBack)
    }
}