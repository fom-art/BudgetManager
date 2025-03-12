package com.wf.bm.feature.settlements.details

import androidx.lifecycle.ViewModel
import com.wf.bm.core.data.repository.SettlementsRepository
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettlementDetailsViewModel(
    private val authenticationManager: AuthenticationManager,
    private val settlementsRepository: SettlementsRepository,
) : ViewModel() {
    private val _settlementDetailsState = MutableStateFlow(SettlementDetailsState())
    val state: StateFlow<SettlementDetailsState> = _settlementDetailsState.asStateFlow()

    init {
        _settlementDetailsState.update {
            it.copy(
                user = authenticationManager.user
            )
        }
    }

    fun setSettlement(settlement: Settlement) {
        _settlementDetailsState.value = _settlementDetailsState.value.copy(settlement = settlement)
    }

    fun deleteSettlement() {
        val state = state.value
        settlementsRepository.deleteSettlementForUser(
            settlement = state.settlement ?: return,
            user = state.user ?: return
        )
        _settlementDetailsState.update { it.copy(shouldNavigateBack = true) }
    }
}

data class SettlementDetailsState(
    val user: User? = null,
    val settlement: Settlement? = null,
    val shouldNavigateBack: Boolean = false
)
