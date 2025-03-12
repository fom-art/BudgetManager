package com.wf.bm.feature.transactions.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.values.iconSizeMedium
import com.wf.bm.core.designsystem.values.paddingLarge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Category
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.RepetitionPeriods
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.utils.toFullDescriptionString
import com.wf.bm.core.designsystem.components.CategoryTag
import com.wf.bm.core.designsystem.layouts.TransactionPositivityIcon
import com.wf.bm.core.designsystem.layouts.DropdownMenuLayout
import java.time.LocalDateTime

@Composable
fun TransactionDetailsScreen(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    deleteTransaction: () -> Unit,
    goToUpdateTransaction: () -> Unit,
    updateTransaction: (Transaction) -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = "",
                goBack = goBack,
                actions = {
                    IconButton(onClick = deleteTransaction) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(
                                R.string.delete
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToUpdateTransaction,
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(com.wf.bm.feature.transactions.R.string.add_transaction)
                )
            }
        }
    ) { innerPadding ->
        TransactionDetailsLayout(
            modifier = Modifier.padding(innerPadding),
            transaction = transaction,
            updateTransaction = updateTransaction
        )
    }
}

@Composable
fun TransactionDetailsLayout(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = paddingMedium)
    ) {

        Spacer(modifier = Modifier.height(paddingLarge))
        Text(
            text = transaction.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(paddingLarge))
        ExpansesSection(transaction = transaction, updateTransaction = updateTransaction)
        Spacer(modifier = Modifier.height(paddingLarge))
        TimeSection(transaction = transaction)
        Spacer(modifier = Modifier.height(paddingLarge))
        CategoriesSection(transaction = transaction)
    }
}

@Composable
fun TimeSection(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    Column(modifier) {
        Text(
            text = stringResource(com.wf.bm.feature.transactions.R.string.date),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(paddingMedium))
        Text(
            text = transaction.date.toFullDescriptionString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    FlowRow(
        modifier = modifier
    ) {
        transaction.categories.forEach { category ->
            CategoryTag(title = category.title)
        }
    }
}

@Composable
fun ExpansesSection(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TransactionPositivityIcon(
            modifier = Modifier.size(iconSizeMedium),
            isPositive = transaction.isPositive,
            changePositivity = { isPositive -> updateTransaction(transaction.copy(isPositive = isPositive)) }
        )
        Spacer(modifier = Modifier.width(paddingMedium))
        Text(
            text = transaction.amount.toString(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(paddingMedium))
        DropdownMenuLayout(
            selectedItem = transaction.currency.sign,
            items = Currency.entries.map { it.currencyNameRes },
            selectAnItem = { currencyNameRes ->
                updateTransaction(
                    transaction.copy(
                        currency = Currency.findCurrencyNameRes(
                            currencyNameRes
                        )!!
                    )
                )
            }
        )
    }
}


@Preview
@Composable
fun TransactionDetailsScreenPreview() {
    var transaction by remember {
        mutableStateOf(
            Transaction(
                title = "Coffee",
                isPositive = false,
                amount = 3.5,
                currency = Currency.USD,
                date = LocalDateTime.now(),
                categories = listOf(
                    Category(title = "Beverages"),
                    Category(title = "Coffee"),
                    Category(title = "Life"),
                    Category(title = "I don't even like coffee"),
                ),
                repeatableTransaction = RepetitionPeriods.DAY
            )
        )
    }
    BudgetManagerTheme {
        TransactionDetailsScreen(
            transaction = transaction,
            goToUpdateTransaction = {},
            deleteTransaction = {},
            goBack = {},
            updateTransaction = { newTransaction -> transaction = newTransaction }
        )
    }
}