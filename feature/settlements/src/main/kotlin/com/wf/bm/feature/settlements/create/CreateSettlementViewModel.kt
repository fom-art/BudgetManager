package com.wf.bm.feature.settlements.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.SettlementsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateSettlementViewModel(
    private val authenticationManager: AuthenticationManager,
    private val settlementsRepository: SettlementsRepository
) : ViewModel() {
    private val _createSettlementState = MutableStateFlow(CreateSettlementState())
    val state: StateFlow<CreateSettlementState> = _createSettlementState.asStateFlow()

    fun setIsLoan(isLoan: Boolean) {
        _createSettlementState.value = _createSettlementState.value.copy(isLoan = isLoan)
    }

    fun setAmount(amount: Double) {
        _createSettlementState.value = _createSettlementState.value.copy(amount = amount)
    }

    fun setCurrency(currency: Currency) {
        _createSettlementState.value = _createSettlementState.value.copy(currency = currency)
    }

    fun setDateTime(dateTime: LocalDateTime) {
        _createSettlementState.value = _createSettlementState.value.copy(dateTime = dateTime)
    }

    fun setSelectedFriend(friend: User) {
        _createSettlementState.value = _createSettlementState.value.copy(selectedFriend = friend)
    }

    fun removeSelectedFriend() {
        _createSettlementState.value = _createSettlementState.value.copy(selectedFriend = null)
    }

    fun submit() {
        val state = state.value
        val currentUser = authenticationManager.user ?: return

        // Determine the debtor and creditor based on whether it's a loan or a debt
        val debtor = if (state.isLoan) state.selectedFriend ?: return else currentUser
        val creditor = if (state.isLoan) currentUser else state.selectedFriend ?: return

        // Create the settlement
        val settlement = Settlement(
            debtor = debtor,
            creditor = creditor,
            status = SettlementStatus.SEND,
            amount = state.amount,
            currency = state.currency,
            creationDateTime = state.dateTime
        )

        // Submit the settlement to the repository
        viewModelScope.launch {
            settlementsRepository.createSettlementForUser(settlement, currentUser)
        }
        _createSettlementState.update { it.copy(shouldNavigateBack = true) }
    }
}

data class CreateSettlementState(
    val isLoan: Boolean = true,
    val amount: Double = 0.0,
    val currency: Currency = Currency.USD,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val selectedFriend: User? = null,
    val shouldNavigateBack: Boolean = false
)

data class CreateSettlementActions(
    val setIsLoan: (Boolean) -> Unit,
    val setAmount: (Double) -> Unit,
    val setCurrency: (Currency) -> Unit,
    val setDateTime: (LocalDateTime) -> Unit,
    val setSelectedFriend: (User) -> Unit,
    val removeSelectedFriend: () -> Unit,
    val showFriendSelectionDialog: () -> Unit,
    val submit: () -> Unit,
)