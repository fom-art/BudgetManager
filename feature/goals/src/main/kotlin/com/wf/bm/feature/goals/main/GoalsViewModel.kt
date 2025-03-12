package com.wf.bm.feature.goals.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.data.repository.GoalsRepository
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GoalsViewModel(
    private val goalsRepository: GoalsRepository,
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _goalsState = MutableStateFlow(GoalsState())
    val state: StateFlow<GoalsState> = _goalsState.asStateFlow()

    init {
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _goalsState.update {
                    it.copy(user = user)
                }
            }
        }
    }

    fun updateGoal(updatedGoal: Goal) {
        viewModelScope.launch {
            goalsRepository.updateGoalForUser(
                user = state.value.user ?: return@launch,
                goal = updatedGoal
            )
        }
    }

    fun deleteGoal(goalToDelete: Goal) {
        viewModelScope.launch {
            goalsRepository.deleteGoalForUser(
                user = state.value.user ?: return@launch,
                goal = goalToDelete
            )
        }
    }

    data class GoalsState(
        val user: User? = null,
    ) {
        val goals: List<Goal>
            get() = user?.goals ?: emptyList()
    }
}

