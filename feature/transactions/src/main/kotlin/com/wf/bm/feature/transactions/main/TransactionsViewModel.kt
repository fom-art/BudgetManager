package com.wf.bm.feature.transactions.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.TransactionsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val authenticationManager: AuthenticationManager,
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _state.update {
                    it.copy(
                        user = user,
                    )
                }
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        transactionsRepository.deleteTransactionForUser(
            user = state.value.user ?: return,
            transaction = transaction
        )
    }

    fun setCreateTransactionDialogVisibility(isVisible: Boolean) {
        _state.update {
            it.copy(
                isCreateTransactionDialogVisible = isVisible
            )
        }
    }
}

data class TransactionsState(
    val user: User? = null,
    val isCreateTransactionDialogVisible: Boolean = false,
) {
    val transactions: List<Transaction>
        get() = user?.transactions ?: emptyList()
}
