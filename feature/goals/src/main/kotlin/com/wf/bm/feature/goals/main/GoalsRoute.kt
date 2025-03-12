package com.wf.bm.feature.goals.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.Goal
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoalsRoute(
    modifier: Modifier = Modifier,
    viewModel: GoalsViewModel = koinViewModel(),
    goBack: () -> Unit,
    goToAddGoal: () -> Unit,
    goToGoalDetails: (Goal) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    GoalsScreen(
        modifier = modifier,
        goals = state.goals,
        updateGoal = viewModel::updateGoal,
        deleteGoal = viewModel::deleteGoal,
        goBack = goBack,
        goToAddGoal = goToAddGoal,
        goToGoalDetails = goToGoalDetails
    )
}
