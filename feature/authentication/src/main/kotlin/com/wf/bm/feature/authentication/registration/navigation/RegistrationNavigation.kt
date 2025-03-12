package com.wf.bm.feature.authentication.registration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.authentication.email_sent.navigation.EmailSentScreen
import com.wf.bm.feature.authentication.registration.RegistrationRoute
import kotlinx.serialization.Serializable

@Serializable
data object RegistrationScreen

fun NavController.navigateToRegistration(navOptions: NavOptions? = null) =
    navigate(RegistrationScreen, navOptions)

fun NavGraphBuilder.registrationScreen(
    goToSignIn: () -> Unit,
    goToRegistrationComplete: () -> Unit
) {
    composable<RegistrationScreen> {
        RegistrationRoute(
            goToSignIn = goToSignIn,
            goToRegistrationComplete = goToRegistrationComplete
        )
    }
}