package com.wf.bm.feature.friends.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.data.repository.UserRepository
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendDetailsViewModel(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _friendDetailsState = MutableStateFlow(FriendDetailsState())
    val state = _friendDetailsState.asStateFlow()

    init {
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _friendDetailsState.update {
                    it.copy(user = user)
                }
            }
        }
    }

    fun setFriend(friend: User) {
        _friendDetailsState.update {
            it.copy(
                friend = friend
            )
        }
    }

    fun closeTotalDebt() {
        viewModelScope.launch {
            userRepository.closeSettlementsBetweenUsers(
                state.value.user ?: return@launch,
                state.value.friend
            )
        }
    }

    fun deleteFriend() {
        viewModelScope.launch {
            userRepository.removeFriendFromUser(
                user = state.value.user ?: return@launch,
                friend = state.value.friend
            )
        }
        _friendDetailsState.update { it.copy(shouldNavigateBack = true) }
    }


    data class FriendDetailsState(
        val user: User? = null,
        val friend: User = User(),
        val totalDebt: Int = 0,
        val totalDebtCurrency: Currency = Currency.USD,
        val shouldNavigateBack: Boolean = false
    )
}
