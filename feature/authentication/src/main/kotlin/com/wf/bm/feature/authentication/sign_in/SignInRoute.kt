package com.wf.bm.feature.authentication.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.data.util.UiEvent
import com.wf.bm.core.model.TextInputModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInRoute(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = koinViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    goToForgetPassword: () -> Unit,
    goToRegistration: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(state.signInError) {
        if (!state.signInError.isNullOrEmpty()) {
            onShowSnackbar("Sign In", state.signInError)
        }
    }

    SignInScreen(
        modifier = modifier,
        emailInputModel = TextInputModel(
            value = state.email,
            errorMessage = state.emailError,
            onChangeValue = viewModel::updateEmail
        ),
        passwordInputModel = TextInputModel(
            value = state.password,
            errorMessage = state.passwordError,
            onChangeValue = viewModel::updatePassword
        ),
        isPasswordVisible = state.isPasswordVisible,
        setPasswordVisibility = { viewModel.togglePasswordVisibility() },
        goToForgetPassword = goToForgetPassword,
        goToRegistration = goToRegistration,
        isFormValid = state.isFormValid,
        signIn = viewModel::signIn,
        isSignInFailed = state.isSignInFailed,
    )
}
