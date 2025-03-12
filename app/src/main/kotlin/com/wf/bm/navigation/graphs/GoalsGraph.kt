package com.wf.bm.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.wf.bm.feature.goals.create.navigation.createGoalScreen
import com.wf.bm.feature.goals.create.navigation.navigateToCreateGoal
import com.wf.bm.feature.goals.main.navigation.GoalsScreen
import com.wf.bm.feature.goals.main.navigation.goalsScreen
import kotlinx.serialization.Serializable

@Serializable
data object GoalsGraph

fun NavGraphBuilder.goalsGraph(
    navController: NavController,
) {
    navigation<GoalsGraph>(
        startDestination = GoalsScreen,
    ) {
        goalsScreen(
            goBack = navController::popBackStack,
            goToAddGoal = navController::navigateToCreateGoal,
            goToGoalDetails = { }
        )
        createGoalScreen(
            goBack = navController::popBackStack
        )
    }
}