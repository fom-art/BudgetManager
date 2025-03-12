package com.wf.bm.feature.authentication.email_sent.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.authentication.email_sent.EmailSentScreen
import kotlinx.serialization.Serializable

@Serializable
object EmailSentScreen

fun NavController.navigateToEmailSent(navOptions: NavOptions? = null) =
    navigate(EmailSentScreen, navOptions)

fun NavGraphBuilder.emailSentScreen(goToSignIn: () -> Unit) {
    composable<EmailSentScreen> {
        EmailSentScreen(
            email = "email",
            goToSignIn = goToSignIn
        )
    }
}