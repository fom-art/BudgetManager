package com.wf.bm.core.data.repository

import com.wf.bm.core.data.database.GoalsDatabase
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.User

class GoalsRepositoryImpl(
    private val goalsDatabase: GoalsDatabase
) : GoalsRepository {
    override suspend fun createGoalForUser(user: User, goal: Goal) {
        goalsDatabase.addGoal(goal)
    }

    override suspend fun updateGoalForUser(user: User, goal: Goal) {
        goalsDatabase.updateGoal(goal)
    }

    override suspend fun deleteGoalForUser(user: User, goal: Goal) {
        goalsDatabase.deleteGoal(goal)
    }
}
