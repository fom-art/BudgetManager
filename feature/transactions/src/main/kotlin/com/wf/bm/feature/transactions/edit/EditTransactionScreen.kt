package com.wf.bm.feature.transactions.edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton
import com.wf.bm.core.designsystem.layouts.expenses.ExpensesLayout
import com.wf.bm.core.designsystem.layouts.time.DateTimeLayout
import com.wf.bm.core.designsystem.values.paddingLarge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Transaction
import com.wf.bm.feature.transactions.create.TransactionNameTextField
import com.wf.bm.feature.transactions.create.category.CategoriesLayout
import com.wf.bm.feature.transactions.create.repeatability.RepeatabilitySetUpLayout

@Composable
fun EditTransactionScreen(
    modifier: Modifier,
    updateTransaction: (Transaction) -> Unit,
    submit: () -> Unit,
    transaction: Transaction,
    goBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            DefaultTopBar(goBack = goBack)
        }
    ) { innerPadding ->
        EditTransactionLayout(
            modifier = modifier.padding(innerPadding),
            transaction = transaction,
            updateTransaction = updateTransaction,
            submit = submit
        )
    }
}

@Composable
fun EditTransactionLayout(
    modifier: Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit,
    submit: () -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = paddingMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(paddingLarge))
            TransactionNameTextField(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingLarge))
            ExpensesLayout(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingLarge))
            Text(
                text = stringResource(R.string.time),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            DateTimeLayout(
                modifier = Modifier.padding(16.dp),
                date = transaction.date,
                updateDate = { newDate -> updateTransaction(transaction.copy(date = newDate)) }
            )
            Spacer(Modifier.height(paddingMedium))
            CategoriesLayout(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingMedium))
            RepeatabilitySetUpLayout(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingLarge))
            BmFilledButton(
                text = stringResource(com.wf.bm.core.designsystem.R.string.submit),
                onClick = submit
            )
            Spacer(Modifier.height(paddingLarge))
        }
    }
}