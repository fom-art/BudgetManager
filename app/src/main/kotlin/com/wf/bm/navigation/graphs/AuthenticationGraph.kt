package com.wf.bm.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.wf.bm.feature.authentication.email_sent.navigation.emailSentScreen
import com.wf.bm.feature.authentication.email_sent.navigation.navigateToEmailSent
import com.wf.bm.feature.authentication.forgot_password.navigation.forgotPasswordScreen
import com.wf.bm.feature.authentication.forgot_password.navigation.navigateToForgotPassword
import com.wf.bm.feature.authentication.registration.navigation.navigateToRegistration
import com.wf.bm.feature.authentication.registration.navigation.registrationScreen
import com.wf.bm.feature.authentication.registration_complete.navigation.navigateToRegistrationComplete
import com.wf.bm.feature.authentication.registration_complete.navigation.registrationCompleteScreen
import com.wf.bm.feature.authentication.sign_in.navigation.SignInScreen
import com.wf.bm.feature.authentication.sign_in.navigation.navigateToSignIn
import com.wf.bm.feature.authentication.sign_in.navigation.signInScreen
import kotlinx.serialization.Serializable

@Serializable
data object AuthenticationGraph

fun NavGraphBuilder.authenticationGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    ) {
    navigation<AuthenticationGraph>(
        startDestination = SignInScreen,
    ) {
        signInScreen(
            goToForgetPassword = navController::navigateToForgotPassword,
            goToRegistration = navController::navigateToRegistration,
            onShowSnackbar = onShowSnackbar
        )
        registrationScreen(
            goToSignIn = navController::navigateToSignIn,
            goToRegistrationComplete = navController::navigateToRegistrationComplete
        )
        registrationCompleteScreen(
            goToSignIn = navController::navigateToSignIn
        )
        forgotPasswordScreen(
            goToEmailSent = navController::navigateToEmailSent,
            goToSignIn = navController::navigateToSignIn
        )
        emailSentScreen(goToSignIn = navController::navigateToSignIn)
    }
}