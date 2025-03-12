package com.wf.bm.feature.transactions.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Category
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Transaction
import com.wf.bm.feature.transactions.R
import java.time.LocalDateTime

@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
    deleteTransaction: (Transaction) -> Unit,
    goToTransactionDetails: (Transaction) -> Unit,
    addTransaction: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(paddingMedium),
                onClick = addTransaction,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_transaction)
                )
            }
        },
    ) { innerPadding ->
        TransactionMainBody(
            modifier = Modifier.padding(innerPadding),
            transactions = transactions,
            deleteTransaction = deleteTransaction,
            goToTransactionDetails = goToTransactionDetails
        )
    }
}

@Composable
fun TransactionMainBody(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
    deleteTransaction: (Transaction) -> Unit,
    goToTransactionDetails: (Transaction) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(transactions) { transaction ->
            TransactionDismissibleListItem(
                modifier = Modifier.clickable { goToTransactionDetails(transaction) },
                transaction = transaction,
                onRemove = deleteTransaction
            )
        }
    }
}

@Preview
@Composable
fun TransactionMainScreenPreview() {
    BudgetManagerTheme {
        val transactions = remember {
            mutableStateListOf(
                Transaction(
                    id = 1,
                    title = "Salary",
                    isPositive = true,
                    amount = 2500.0,
                    currency = Currency.USD,
                    date = LocalDateTime.now().minusDays(1),
                    categories = listOf(Category(title = "Income"))
                ),
                Transaction(
                    id = 2,
                    title = "Groceries",
                    isPositive = false,
                    amount = 120.75,
                    currency = Currency.USD,
                    date = LocalDateTime.now().minusDays(2),
                    categories = listOf(Category(title = "Food"), Category(title = "Essentials"))
                ),
                Transaction(
                    id = 3,
                    title = "Gym Membership",
                    isPositive = false,
                    amount = 50.0,
                    currency = Currency.USD,
                    date = LocalDateTime.now().minusWeeks(1),
                    categories = listOf(Category(title = "Fitness"))
                ),
                Transaction(
                    id = 4,
                    title = "Coffee",
                    isPositive = false,
                    amount = 4.5,
                    currency = Currency.USD,
                    date = LocalDateTime.now().minusDays(3),
                    categories = listOf(Category(title = "Beverage"))
                ),
                Transaction(
                    id = 5,
                    title = "Freelance Project",
                    isPositive = true,
                    amount = 1200.0,
                    currency = Currency.USD,
                    date = LocalDateTime.now().minusWeeks(2),
                    categories = listOf(Category(title = "Work"))
                )
            )
        }

        TransactionsScreen(
            transactions = transactions,
            deleteTransaction = { transaction -> transactions.remove(transaction) },
            goToTransactionDetails = { /* Navigate to details */ },
            addTransaction = { /* Add a new transaction */ }
        )
    }
}
