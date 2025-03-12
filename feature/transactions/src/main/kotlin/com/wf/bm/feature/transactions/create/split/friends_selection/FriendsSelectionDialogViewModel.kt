package com.wf.bm.feature.transactions.create.split.friends_selection

import com.wf.bm.core.model.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.util.AuthenticationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendsSelectionDialogViewModel(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _friendsSelectionDialogState = MutableStateFlow(FriendsSelectionDialogState())
    val state: StateFlow<FriendsSelectionDialogState> = _friendsSelectionDialogState.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _friendsSelectionDialogState.update {
                    it.copy(
                        friends = authenticationManager.user?.friends ?: emptyList()
                    )
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        _friendsSelectionDialogState.update {
            it.copy(searchQuery = query)
        }
    }

    fun addFriend(user: User) {
        _friendsSelectionDialogState.update { state ->
            state.copy(selectedFriends = state.selectedFriends + user)
        }
    }

    fun removeFriend(user: User) {
        _friendsSelectionDialogState.update { state ->
            state.copy(selectedFriends = state.selectedFriends - user)
        }
    }

    fun setDialogVisibility(isVisible: Boolean) {
        _friendsSelectionDialogState.update {
            it.copy(isVisible = isVisible)
        }
    }

    fun resetState() {
        _friendsSelectionDialogState.value = FriendsSelectionDialogState()
        _friendsSelectionDialogState.update {
            it.copy(
                friends = authenticationManager.user?.friends ?: emptyList()
            )
        }
    }
}

data class FriendsSelectionDialogState(
    val friends: List<User> = emptyList(),
    val selectedFriends: List<User> = emptyList(),
    val searchQuery: String = "",
    val isVisible: Boolean = false
) {
    val filteredFriends
        get() = friends.filter { user ->
            user.name.contains(searchQuery, ignoreCase = true) ||
                    user.nickname.contains(searchQuery, ignoreCase = true)
        }
}

data class FriendsSelectionDialogActions(
    val setSearchQuery: (String) -> Unit,
    val onDismissRequest: () -> Unit,
    val addCustomFriend: (User) -> Unit,
    val addFriend: (User) -> Unit,
    val removeFriend: (User) -> Unit,
    val submit: () -> Unit,
    val setDialogVisibility: (Boolean) -> Unit
)
