package com.wf.bm.feature.friends.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.data.repository.UserRepository
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _friendsState = MutableStateFlow(FriendsState())
    val state: StateFlow<FriendsState> = _friendsState.asStateFlow()

    init {
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _friendsState.update {
                    it.copy(user = user)
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _friendsState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            _friendsState.update {
                it.copy(
                    suggestedUsers = userRepository.findUsersByNickname(query)
                )
            }
        }
    }

    fun deleteFriend(friend: User) {
        viewModelScope.launch {
            try {
                userRepository.removeFriendFromUser(
                    user = state.value.user ?: return@launch,
                    friend = friend
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    data class FriendsState(
        val user: User? = null,
        val searchQuery: String = "",
        val suggestedUsers: List<User> = emptyList()
    ) {
        val friends: List<User>
            get() = user?.friends ?: emptyList()
    }
}
