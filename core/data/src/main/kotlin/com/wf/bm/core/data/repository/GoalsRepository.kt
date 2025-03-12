package com.wf.bm.core.data.repository

import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.User

interface GoalsRepository {
    suspend fun createGoalForUser(user: User, goal: Goal)
    suspend fun updateGoalForUser(user: User, goal: Goal)
    suspend fun deleteGoalForUser(user: User, goal: Goal)
}
