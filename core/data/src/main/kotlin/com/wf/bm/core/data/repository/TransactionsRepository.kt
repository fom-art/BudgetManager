package com.wf.bm.core.data.repository

import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User

interface TransactionsRepository {
    fun createTransactionForUser(user: User, transaction: Transaction)
    fun updateTransactionForUser(user: User, transaction: Transaction)
    fun deleteTransactionForUser(user: User, transaction: Transaction)
    suspend fun observeTransaction(transaction: Transaction, action: (Transaction) -> Unit)
}