package com.wf.bm.core.data.repository

import com.wf.bm.core.data.database.TransactionsDatabase
import com.wf.bm.core.model.Category
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter

class TransactionsRepositoryImpl(
    private val transactionsDatabase: TransactionsDatabase
) : TransactionsRepository {

    override fun createTransactionForUser(user: User, transaction: Transaction) {
        val updatedTransaction = transaction.copy(
            categories = transaction.categories.map { category ->
                category.copy(transactions = category.transactions + transaction)
            }
        )
        transactionsDatabase.addTransaction(updatedTransaction)
    }

    override fun updateTransactionForUser(user: User, transaction: Transaction) {
        val updatedTransaction = transaction.copy(
            categories = transaction.categories.map { category ->
                category.copy(transactions = category.transactions.map {
                    if (it.id == transaction.id) transaction else it
                })
            }
        )
        transactionsDatabase.updateTransaction(updatedTransaction)
    }

    override fun deleteTransactionForUser(user: User, transaction: Transaction) {
        val updatedTransaction = transaction.copy(
            categories = transaction.categories.map { category ->
                category.copy(transactions = category.transactions.filterNot { it.id == transaction.id })
            }
        )
        transactionsDatabase.deleteTransaction(updatedTransaction)
    }

    override suspend fun observeTransaction(transaction: Transaction, action: (Transaction) -> Unit) {
        transactionsDatabase.transactions.filter { transactions ->
            transactions.any { it.id == transaction.id }
        }.collectLatest { transactions ->
            transactions.find { it.id == transaction.id }?.let { action(it) }
        }
    }
}
