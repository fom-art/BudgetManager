package com.wf.bm.core.data.database

import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.GoalType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoalsDatabase {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals: StateFlow<List<Goal>> = _goals

    fun addGoal(goal: Goal) {
        _goals.value = _goals.value + goal
    }

    fun updateGoal(goal: Goal) {
        _goals.value = _goals.value.map {
            if (it.name == goal.name && it.currency == goal.currency) goal else it
        }
    }

    fun deleteGoal(goal: Goal) {
        _goals.value = _goals.value.filterNot { 
            it.name == goal.name && it.currency == goal.currency 
        }
    }

    fun getGoalsForUser(userId: Long): List<Goal> {
        return _goals.value.filter { it.goalType == GoalType.EXPENSE || it.goalType == GoalType.INCOME }
    }
}
