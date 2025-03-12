package com.wf.bm.feature.goals.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateGoalRoute(
    modifier: Modifier = Modifier,
    viewModel: CreateGoalViewModel = koinViewModel(),
    goBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(state.shouldNavigateBack) {
        if (state.shouldNavigateBack) {
            goBack()
        }
    }

    CreateGoalScreen(
        modifier = modifier,
        goal = state.goal,
        updateGoal = viewModel::updateGoal,
        submit = viewModel::submitGoal,
        goBack = goBack
    )
}
