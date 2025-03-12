package com.wf.bm.feature.transactions.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.Transaction
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditTransactionRoute(
    modifier: Modifier = Modifier,
    viewModel: EditTransactionViewModel = koinViewModel(),
    transaction: Transaction,
    goBack: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    viewModel.setTransaction(transaction)

    LaunchedEffect(state.shouldNavigateBack) {
        if (state.shouldNavigateBack) {
            goBack()
        }
    }

    EditTransactionScreen(
        modifier = modifier,
        transaction = state.transaction,
        updateTransaction = { updatedTransaction ->
            viewModel.updateTransaction(updatedTransaction)
        },
        submit = { viewModel.submitTransactionUpdate() },
        goBack = goBack
    )
}
