package com.wf.bm.feature.transactions.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.TransactionsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditTransactionViewModel(
    private val authenticationManager: AuthenticationManager,
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    private val _editTransactionState = MutableStateFlow(EditTransactionState())
    val state: StateFlow<EditTransactionState> = _editTransactionState

    fun setTransaction(transaction: Transaction) {
        _editTransactionState.update {
            it.copy(
                transaction = transaction
            )
        }
        viewModelScope.launch {
            transactionsRepository.observeTransaction(transaction) { transaction ->
                _editTransactionState.update {
                    it.copy(
                        transaction = transaction
                    )
                }
            }
        }
    }

    fun updateTransaction(transaction: Transaction) {
        _editTransactionState.update { it.copy(transaction = transaction) }
    }

    fun submitTransactionUpdate() {
        transactionsRepository.updateTransactionForUser(
            user = authenticationManager.user ?: return,
            transaction = state.value.transaction
        )
        _editTransactionState.update { it.copy(shouldNavigateBack = true) }
    }
}

data class EditTransactionState(
    val transaction: Transaction = Transaction(),
    val shouldNavigateBack: Boolean = false
)
