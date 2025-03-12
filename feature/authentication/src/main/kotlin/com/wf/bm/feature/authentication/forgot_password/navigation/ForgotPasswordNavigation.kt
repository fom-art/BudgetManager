package com.wf.bm.feature.authentication.forgot_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.authentication.forgot_password.ForgotPasswordRoute
import kotlinx.serialization.Serializable

@Serializable
data object ForgotPasswordScreen

fun NavController.navigateToForgotPassword(navOptions: NavOptions? = null) =
    navigate(ForgotPasswordScreen, navOptions)

fun NavGraphBuilder.forgotPasswordScreen(
    goToEmailSent: () -> Unit,
    goToSignIn: () -> Unit
) {
    composable<ForgotPasswordScreen> {
        ForgotPasswordRoute(
            goToEmailSent = goToEmailSent,
            goToSignIn = goToSignIn
        )
    }
}