package com.wf.bm.feature.authentication.sign_in

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton
import com.wf.bm.core.designsystem.components.EmailTextField
import com.wf.bm.core.designsystem.components.PasswordTextField
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.values.paddingLarge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.designsystem.values.paddingSmall
import com.wf.bm.core.designsystem.values.paddingXLarge
import com.wf.bm.core.model.TextInputModel


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    emailInputModel: TextInputModel,
    passwordInputModel: TextInputModel,
    isPasswordVisible: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    isSignInFailed: Boolean,
    goToForgetPassword: () -> Unit,
    goToRegistration: () -> Unit,
    isFormValid: Boolean,
    signIn: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedGradientBackground()

        SignInForm(
            modifier = modifier,
            emailInputModel = emailInputModel,
            passwordInputModel = passwordInputModel,
            isPasswordVisible = isPasswordVisible,
            setPasswordVisibility = setPasswordVisibility,
            isSignInFailed = isSignInFailed,
            goToForgetPassword = goToForgetPassword,
            goToRegistration = goToRegistration,
            isFormValid = isFormValid,
            goToHomepage = signIn
        )
    }
}

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    emailInputModel: TextInputModel,
    passwordInputModel: TextInputModel,
    isPasswordVisible: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    isSignInFailed: Boolean,
    goToForgetPassword: () -> Unit,
    goToRegistration: () -> Unit,
    isFormValid: Boolean,
    goToHomepage: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(paddingMedium)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = paddingSmall,
                        vertical = paddingXLarge
                    )
                    .animateContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(com.wf.bm.feature.authentication.R.string.sign_in),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(paddingMedium))

                EmailTextField(
                    modifier = Modifier.padding(paddingSmall),
                    email = emailInputModel.value,
                    emailError = emailInputModel.errorMessage,
                    onEmailValueChange = emailInputModel.onChangeValue
                )

                Spacer(modifier = Modifier.height(paddingMedium))

                PasswordTextField(
                    modifier = Modifier.padding(paddingSmall),
                    password = passwordInputModel.value,
                    passwordError = passwordInputModel.errorMessage,
                    isPasswordVisible = isPasswordVisible,
                    onPasswordValueChange = passwordInputModel.onChangeValue,
                    setPasswordVisibility = setPasswordVisibility
                )

                Spacer(modifier = Modifier.height(paddingSmall))
                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.End), visible = isSignInFailed
                ) {
                    Spacer(modifier = Modifier.height(paddingSmall))
                    Text(
                        text = stringResource(R.string.sign_in_failed),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
                ForgotPasswordText(
//                    modifier = Modifier
//                        .align(Alignment.End),
                    onClick = goToForgetPassword
                )




                Spacer(modifier = Modifier.height(paddingSmall))

                BmFilledButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = stringResource(com.wf.bm.feature.authentication.R.string.sign_in),
                    onClick = goToHomepage,
                    enabled = isFormValid
                )

                Spacer(modifier = Modifier.height(paddingLarge))

                RegistrationPrompt(onClick = goToRegistration)
            }
        }
    }
}

@Composable
fun ForgotPasswordText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = stringResource(com.wf.bm.feature.authentication.R.string.forgot_password),
            style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.Underline),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun RegistrationPrompt(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(com.wf.bm.feature.authentication.R.string.dont_have_an_account),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(com.wf.bm.feature.authentication.R.string.register),
            style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.Underline),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = dimensionResource(R.dimen.padding_xsmall))
                .clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
fun SignInScreenPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    // Creating models with error handling directly in `onChangeValue`.
    val emailInputModel = TextInputModel(
        value = email,
        errorMessage = emailError,
        onChangeValue = { newEmail ->
            email = newEmail
            emailError = if (newEmail.contains("@")) null else "Invalid email!"
        }
    )

    val passwordInputModel = TextInputModel(
        value = password,
        errorMessage = passwordError,
        onChangeValue = { newPassword ->
            password = newPassword
            passwordError = if (newPassword.length < 8) "Minimum 8 characters required" else null
        }
    )

    // Validate form based on email and password inputs
    val isFormValid = emailInputModel.errorMessage == null &&
            passwordInputModel.errorMessage == null &&
            email.isNotEmpty() &&
            password.isNotEmpty()

    BudgetManagerTheme {
        SignInScreen(
            emailInputModel = emailInputModel,
            passwordInputModel = passwordInputModel,
            isPasswordVisible = isPasswordVisible,
            setPasswordVisibility = { isPasswordVisible = it },
            goToForgetPassword = {},
            goToRegistration = {},
            isFormValid = isFormValid,
            signIn = {},
            isSignInFailed = false
        )
    }
}