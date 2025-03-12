package com.wf.bm.feature.authentication.sign_in.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.authentication.email_sent.navigation.EmailSentScreen
import com.wf.bm.feature.authentication.sign_in.SignInRoute
import kotlinx.serialization.Serializable

@Serializable
data object SignInScreen

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) =
    navigate(SignInScreen, navOptions)

fun NavGraphBuilder.signInScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    goToForgetPassword: () -> Unit,
    goToRegistration: () -> Unit,
) {
    composable<SignInScreen> {
        SignInRoute(
            goToForgetPassword = goToForgetPassword,
            goToRegistration = goToRegistration,
            onShowSnackbar = onShowSnackbar
        )
    }
}