package com.wf.bm.feature.goals.create

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

class CreateGoalViewModel(
    private val goalsRepository: GoalsRepository,
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _createGoalState = MutableStateFlow(CreateGoalState())
    val state: StateFlow<CreateGoalState> = _createGoalState.asStateFlow()

    init {
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _createGoalState.update {
                    it.copy(user = user)
                }
            }
        }
    }

    fun updateGoal(newGoal: Goal) {
        _createGoalState.update { it.copy(goal = newGoal) }
    }

    fun submitGoal() {
        viewModelScope.launch {
            goalsRepository.createGoalForUser(
                user = state.value.user ?: return@launch,
                goal = state.value.goal
            )
        }
        _createGoalState.update { it.copy(shouldNavigateBack = true) }
    }

    data class CreateGoalState(
        val user: User? = null,
        val goal: Goal = Goal(),
        val shouldNavigateBack: Boolean = false
    )
}
