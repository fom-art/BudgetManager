package com.wf.bm.core.data.database

import com.wf.bm.core.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TransactionsDatabase {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    fun addTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value + transaction
    }

    fun updateTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value.map {
            if (it.id == transaction.id) transaction else it
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value.filterNot { it.id == transaction.id }
    }

    /**
     * Fetch transactions directly associated with a user by their ID.
     */
    fun getTransactionsForUser(userId: Long): List<Transaction> {
        return _transactions.value.filter { transaction ->
            transaction.categories.any { category ->
                category.transactions.any { it.id == userId }
            } || transaction.id == userId // Include if transaction ID matches the user ID.
        }
    }

    /**
     * Fetch transactions associated with user categories.
     */
    fun getTransactionsForUserCategories(userId: Long): List<Transaction> {
        return _transactions.value.filter { transaction ->
            transaction.categories.any { category ->
                category.transactions.any { it.id == userId }
            }
        }
    }
}
