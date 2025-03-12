package com.wf.bm.feature.transactions.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.TransactionsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionDetailsViewModel(
    private val authenticationManager: AuthenticationManager,
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {
    private val _transactionDetailsState = MutableStateFlow(TransactionDetailsState())
    val state: StateFlow<TransactionDetailsState> = _transactionDetailsState

    fun deleteTransaction(transaction: Transaction) {
        transactionsRepository.deleteTransactionForUser(
            user = authenticationManager.user ?: return,
            transaction
        )
        _transactionDetailsState.update { it.copy(shouldNavigateBack = true) }
    }

    fun setTransaction(transaction: Transaction) {
        _transactionDetailsState.update {
            it.copy(
                transaction = transaction
            )
        }
        viewModelScope.launch {
            transactionsRepository.observeTransaction(transaction) { transaction ->
                _transactionDetailsState.update {
                    it.copy(
                        transaction = transaction
                    )
                }
            }
        }
    }

    fun updateTransaction(transaction: Transaction) {
        transactionsRepository.updateTransactionForUser(
            user = authenticationManager.user ?: return,
            transaction
        )
    }
}

data class TransactionDetailsState(
    val transaction: Transaction = Transaction(),
    val shouldNavigateBack: Boolean = false
)