package com.wf.bm.feature.settlements.main.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterDialogViewModel : ViewModel() {

    private val _state = MutableStateFlow(FilterDialogState())
    val state: StateFlow<FilterDialogState> = _state

    fun setSearchQuery(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        filterSuggestedFriends()
    }

    fun addFriend(user: User) {
        if (!_state.value.selectedFriends.contains(user)) {
            _state.value = _state.value.copy(
                selectedFriends = _state.value.selectedFriends + user
            )
        }
    }

    fun removeFriend(user: User) {
        _state.value = _state.value.copy(
            selectedFriends = _state.value.selectedFriends - user
        )
    }

    fun setDebtChecked(isChecked: Boolean) {
        _state.value = _state.value.copy(isDebtChecked = isChecked)
    }

    fun setLoanChecked(isChecked: Boolean) {
        _state.value = _state.value.copy(isLoanChecked = isChecked)
    }

    fun setDialogVisibility(isVisible: Boolean) {
        _state.value = _state.value.copy(isVisible = isVisible)
    }

    fun submit() {
        // Handle the submission logic, for example, apply the filter settings
        setDialogVisibility(false)
    }

    fun onDismissRequest() {
        setDialogVisibility(false)
    }

    private fun filterSuggestedFriends() {
        val query = _state.value.searchQuery.lowercase()
        viewModelScope.launch {
            val filteredFriends = _state.value.suggestedFriends.filter {
                it.name.lowercase().contains(query) || it.nickname.lowercase().contains(query)
            }
            _state.value = _state.value.copy(suggestedFriends = filteredFriends)
        }
    }

    fun initialize(suggestedFriends: List<User>) {
        _state.value = _state.value.copy(
            suggestedFriends = suggestedFriends,
            searchQuery = "",
            selectedFriends = emptyList()
        )
    }
}


data class FilterDialogState(
    val suggestedFriends: List<User> = emptyList(),
    val selectedFriends: List<User> = emptyList(),
    val isDebtChecked: Boolean = true,
    val isLoanChecked: Boolean = true,
    val searchQuery: String = "",
    val isVisible: Boolean = false
)

data class FilterDialogActions(
    val setSearchQuery: (String) -> Unit,
    val onDismissRequest: () -> Unit,
    val addFriend: (User) -> Unit,
    val removeFriend: (User) -> Unit,
    val submit: () -> Unit,
    val setDialogVisibility: (Boolean) -> Unit,
    val setDebtChecked: (Boolean) -> Unit,
    val setLoanChecked: (Boolean) -> Unit,
)
