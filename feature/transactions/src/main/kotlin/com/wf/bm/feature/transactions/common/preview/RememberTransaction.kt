package com.wf.bm.feature.transactions.common.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.wf.bm.core.model.Category
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Transaction
import java.time.LocalDateTime

@Composable
fun rememberTransactionState(): Pair<Transaction, (Transaction) -> Unit> {
    val sampleCategories = listOf(
        Category(title = "Groceries"),
        Category(title = "Dining Out"),
        Category(title = "Entertainment")
    )

    // Transaction state
    var transaction by remember {
        mutableStateOf(
            Transaction(
                title = "Dinner at Restaurant",
                isPositive = false,
                amount = 45.99,
                currency = Currency.USD,
                date = LocalDateTime.now(),
                categories = sampleCategories,
                repeatableTransaction = null
            )
        )
    }

    val updateTransaction: (Transaction) -> Unit = { updatedTransaction ->
        transaction = updatedTransaction
        println("Transaction updated: $updatedTransaction")
    }

    return transaction to updateTransaction
}


