package com.wf.bm.feature.goals.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.core.model.Goal
import com.wf.bm.feature.goals.main.GoalsRoute
import kotlinx.serialization.Serializable

@Serializable
data object GoalsScreen

fun NavController.navigateToGoals(navOptions: NavOptions? = null) =
    navigate(GoalsScreen, navOptions)

fun NavGraphBuilder.goalsScreen(
    goBack: () -> Unit,
    goToAddGoal: () -> Unit,
    goToGoalDetails: (Goal) -> Unit
) {
    composable<GoalsScreen> {
        GoalsRoute(
            goBack = goBack,
            goToAddGoal = goToAddGoal,
            goToGoalDetails = goToGoalDetails
        )
    }
}