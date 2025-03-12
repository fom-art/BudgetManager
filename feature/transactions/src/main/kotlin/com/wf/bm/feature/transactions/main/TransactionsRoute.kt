package com.wf.bm.feature.transactions.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.Transaction
import com.wf.bm.feature.transactions.create.CreateTransactionBottomSheetDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionsRoute(
    modifier: Modifier = Modifier,
    viewModel: TransactionsViewModel = koinViewModel(),
    goToTransactionDetails: (Transaction) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Box(modifier = modifier.fillMaxSize()) {


        TransactionsScreen(
            modifier = modifier,
            transactions = state.transactions,
            deleteTransaction = { viewModel.deleteTransaction(it) },
            goToTransactionDetails = goToTransactionDetails,
            addTransaction = { viewModel.setCreateTransactionDialogVisibility(true) }
        )

        AnimatedVisibility(
            visible = state.isCreateTransactionDialogVisible,
            enter = slideInVertically(initialOffsetY = { it / 2 }),
            exit = slideOutVertically(targetOffsetY = { it  })
        ) {
            CreateTransactionBottomSheetDialog(
                dismiss = { viewModel.setCreateTransactionDialogVisibility(false) }
            )
        }
    }
}
