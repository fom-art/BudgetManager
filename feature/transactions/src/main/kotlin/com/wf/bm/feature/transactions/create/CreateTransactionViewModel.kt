package com.wf.bm.feature.transactions.create

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

class CreateTransactionViewModel(
    authenticationManager: AuthenticationManager,
    private val transactionRepository: TransactionsRepository
) : ViewModel() {
    private val _createTransactionState = MutableStateFlow(CreateTransactionState())
    val state: StateFlow<CreateTransactionState> = _createTransactionState.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _createTransactionState.update {
                    it.copy(
                        user = user,
                    )
                }
            }
        }
    }

    fun updateTransaction(updatedTransaction: Transaction) {
        _createTransactionState.update {
            it.copy(transaction = updatedTransaction)
        }
    }

    fun updateUserSplit(user: User, amount: String) {
        if (_createTransactionState.value.split != null) {
            _createTransactionState.update { currentState ->
                val updatedSplit = currentState.split!!.toMutableMap().apply { put(user, amount) }
                currentState.copy(split = updatedSplit)
            }
        }
    }

    fun removeSplit() {
        _createTransactionState.update { it.copy(split = emptyMap()) }
    }

    fun setSplitEvenly(isEven: Boolean) {
        _createTransactionState.update { it.copy(isSplitEvenly = isEven) }
    }

    fun submit() {
        val state = state.value
        transactionRepository.createTransactionForUser(
            user = state.user ?: return,
            transaction = state.transaction
        )
        _createTransactionState.update { it.copy(shouldNavigateBack = true) }
    }

    fun resetState() {
        _createTransactionState.update {
            it.copy(
                transaction = Transaction(),
                split = null,
                isSplitEvenly = true,
                shouldNavigateBack = false
            )
        }
    }
}

data class CreateTransactionState(
    val user: User? = null,
    val transaction: Transaction = Transaction(),
    val split: Map<User, String>? = null,
    val isSplitEvenly: Boolean = true,
    val shouldNavigateBack: Boolean = false
)