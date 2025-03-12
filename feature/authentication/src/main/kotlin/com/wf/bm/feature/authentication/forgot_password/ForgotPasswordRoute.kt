package com.wf.bm.feature.authentication.forgot_password

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordRoute(
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel = koinViewModel(),
    goToEmailSent: () -> Unit,
    goToSignIn: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.shouldGoToEmailSent) {
        if (state.shouldGoToEmailSent) {
            goToEmailSent()
        }
    }

    ForgotPasswordScreen(
        modifier = modifier,
        emailInputModel = state.emailInputModel.copy(
            onChangeValue = viewModel::updateEmail
        ),
        isFormValid = state.isFormValid,
        goToEmailSent = goToEmailSent,
        goToSignIn = goToSignIn,
        confirmEmail = viewModel::confirmEmail
    )
}
