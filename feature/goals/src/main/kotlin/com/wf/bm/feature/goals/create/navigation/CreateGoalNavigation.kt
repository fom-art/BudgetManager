package com.wf.bm.feature.goals.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.goals.create.CreateGoalRoute
import kotlinx.serialization.Serializable

@Serializable
data object CreateGoalScreen

fun NavController.navigateToCreateGoal(navOptions: NavOptions? = null) = navigate(CreateGoalScreen, navOptions)

fun NavGraphBuilder.createGoalScreen(
    goBack: () -> Unit
) {
    composable<CreateGoalScreen> {
        CreateGoalRoute(goBack = goBack)
    }
}