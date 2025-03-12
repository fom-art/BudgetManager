package com.wf.bm.feature.settlements.create.friend_selection

import com.wf.bm.core.model.User

import androidx.lifecycle.ViewModel
import com.wf.bm.core.data.util.AuthenticationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FriendSelectionDialogViewModel(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _friendSelectionDialogState = MutableStateFlow(FriendSelectionDialogState())
    val state: StateFlow<FriendSelectionDialogState> = _friendSelectionDialogState.asStateFlow()

    init {
        _friendSelectionDialogState.update {
            it.copy(
                friends = authenticationManager.user?.friends ?: emptyList()
            )
        }
    }

    fun setSearchQuery(query: String) {
        _friendSelectionDialogState.update {
            it.copy(searchQuery = query)
        }
    }

    fun setDialogVisibility(isVisible: Boolean) {
        _friendSelectionDialogState.update {
            it.copy(isVisible = isVisible)
        }
    }

    fun dismissDialog() {
        _friendSelectionDialogState.update {
            it.copy(isVisible = false)
        }
    }
}

data class FriendSelectionDialogState(
    val friends: List<User> = emptyList(),
    val searchQuery: String = "",
    val isVisible: Boolean = false
) {
    val filteredFriends
        get() = friends.filter { user ->
            user.name.contains(searchQuery, ignoreCase = true) ||
                    user.nickname.contains(searchQuery, ignoreCase = true)
        }
}

data class FriendSelectionDialogActions(
    val setSearchQuery: (String) -> Unit,
    val onDismissRequest: () -> Unit,
    val selectCustomFriend: (User) -> Unit,
    val selectFriend: (User) -> Unit,
    val setDialogVisibility: (Boolean) -> Unit
)
