package com.wf.bm.feature.settlements.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.SettlementsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettlementsViewModel(
    authenticationManager: AuthenticationManager,
    private val settlementsRepository: SettlementsRepository
) : ViewModel() {
    private val _settlementsState = MutableStateFlow(SettlementsState())
    val state: StateFlow<SettlementsState> = _settlementsState.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _settlementsState.update {
                    it.copy(
                        user = user,
                    )
                }
            }
        }
    }

    fun deleteSettlement(settlement: Settlement) {
        settlementsRepository.deleteSettlementForUser(
            settlement = settlement,
            user = state.value.user ?: return
        )
    }
}

data class SettlementsState(
    val user: User? = null,
) {
    val notificationsCount: Int
        get() = user?.notifications?.size ?: 0
    val settlements: List<Settlement>
        get() = (user?.debtorSettlements.orEmpty() + user?.creditorSettlements.orEmpty())
            .filter {
                it.status !in setOf(
                    SettlementStatus.CANCELED,
                    SettlementStatus.REJECTED,
                    SettlementStatus.PAID
                )
            }
}