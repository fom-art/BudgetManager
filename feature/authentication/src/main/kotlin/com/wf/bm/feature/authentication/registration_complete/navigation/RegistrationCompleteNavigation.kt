package com.wf.bm.feature.authentication.registration_complete.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.authentication.registration_complete.RegistrationCompletedScreen
import kotlinx.serialization.Serializable

@Serializable
data object RegistrationCompleteScreen

fun NavController.navigateToRegistrationComplete(navOptions: NavOptions? = null) =
    navigate(RegistrationCompleteScreen, navOptions)

fun NavGraphBuilder.registrationCompleteScreen(
    goToSignIn: () -> Unit
) {
    composable<RegistrationCompleteScreen> {
        RegistrationCompletedScreen(
            goToSignIn = goToSignIn
        )
    }
}