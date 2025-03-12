package com.wf.bm.feature.authentication.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationRoute(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = koinViewModel(),
    goToSignIn: () -> Unit,
    goToRegistrationComplete: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigateToRegistrationComplete by viewModel.navigateToRegistrationComplete.collectAsStateWithLifecycle()

    LaunchedEffect(navigateToRegistrationComplete) {
        if (navigateToRegistrationComplete) {
            goToRegistrationComplete()
        }
    }

    RegistrationScreen(
        modifier = modifier,
        usernameInputModel = state.usernameInputModel.copy(onChangeValue = viewModel::updateUsername),
        emailInputModel = state.emailInputModel.copy(onChangeValue = viewModel::updateEmail),
        passwordInputModel = state.passwordInputModel.copy(onChangeValue = viewModel::updatePassword),
        passwordRepeatInputModel = state.passwordRepeatInputModel.copy(onChangeValue = viewModel::updatePasswordRepeat),
        isPasswordVisible = state.isPasswordVisible,
        isPasswordRepeatVisible = state.isPasswordRepeatVisible,
        isFormValid = state.isFormValid,
        setPasswordVisibility = viewModel::togglePasswordVisibility,
        setPasswordRepeatVisibility = viewModel::togglePasswordRepeatVisibility,
        goToSignIn = goToSignIn,
        submit = viewModel::submit
    )
}
