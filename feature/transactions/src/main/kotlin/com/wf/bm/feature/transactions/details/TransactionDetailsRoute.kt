package com.wf.bm.feature.transactions.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.Transaction
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: TransactionDetailsViewModel = koinViewModel(),
    transaction: Transaction,
    goToUpdateTransaction: (Transaction) -> Unit,
    goBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    viewModel.setTransaction(transaction)

    LaunchedEffect(state.shouldNavigateBack) {
        if (state.shouldNavigateBack) {
            goBack()
        }
    }

    TransactionDetailsScreen(
        modifier = modifier,
        transaction = state.transaction,
        goToUpdateTransaction = { goToUpdateTransaction(state.transaction) },
        deleteTransaction = { viewModel.deleteTransaction(state.transaction) },
        goBack = goBack,
        updateTransaction = viewModel::updateTransaction
    )
}
