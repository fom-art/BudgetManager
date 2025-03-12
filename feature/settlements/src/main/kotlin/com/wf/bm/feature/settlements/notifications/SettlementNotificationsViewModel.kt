package com.wf.bm.feature.settlements.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.SettlementsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettlementsNotificationsViewModel(
    authenticationManager: AuthenticationManager,
    private val settlementsRepository: SettlementsRepository
) : ViewModel() {
    private val _settlementsNotificationsState = MutableStateFlow(
        SettlementsNotificationsState()
    )
    val state: StateFlow<SettlementsNotificationsState> = _settlementsNotificationsState

    init {
        _settlementsNotificationsState.update { it.copy(user = authenticationManager.user) }
    }

    fun submitNotification(settlement: Settlement) {
        settlementsRepository.updateSettlementForUser(
            user = state.value.user ?: return,
            settlement = settlement.copy(
                status = SettlementStatus.APPROVED
            )
        )
    }

    fun rejectNotification(settlement: Settlement) {
        settlementsRepository.updateSettlementForUser(
            user = state.value.user ?: return,
            settlement = settlement.copy(
                status = SettlementStatus.REJECTED
            )
        )
    }

    data class SettlementsNotificationsState(
        val user: User? = null,
    ) {
        val settlements: List<Settlement>
            get() = (user?.debtorSettlements ?: emptyList()) +
                    (user?.creditorSettlements ?: emptyList())
    }
}


