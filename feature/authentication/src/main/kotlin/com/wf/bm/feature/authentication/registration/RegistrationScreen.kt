package com.wf.bm.feature.authentication.registration

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton
import com.wf.bm.core.designsystem.components.EmailTextField
import com.wf.bm.core.designsystem.components.PasswordTextField
import com.wf.bm.core.designsystem.components.UsernameTextField
import com.wf.bm.core.model.TextInputModel

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    usernameInputModel: TextInputModel,
    emailInputModel: TextInputModel,
    passwordInputModel: TextInputModel,
    passwordRepeatInputModel: TextInputModel,
    isPasswordVisible: Boolean,
    isPasswordRepeatVisible: Boolean,
    isFormValid: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    setPasswordRepeatVisibility: (Boolean) -> Unit,
    goToSignIn: () -> Unit,
    submit: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedGradientBackground()

        RegistrationForm(
            modifier = modifier,
            usernameInputModel = usernameInputModel,
            emailInputModel = emailInputModel,
            passwordInputModel = passwordInputModel,
            passwordRepeatInputModel = passwordRepeatInputModel,
            isFormValid = isFormValid,
            isPasswordVisible = isPasswordVisible,
            isPasswordRepeatVisible = isPasswordRepeatVisible,
            setPasswordVisibility = setPasswordVisibility,
            setPasswordRepeatVisibility = setPasswordRepeatVisibility,
            goToSignIn = goToSignIn,
            submit = submit
        )
    }
}

@Composable
fun RegistrationForm(
    modifier: Modifier = Modifier,
    usernameInputModel: TextInputModel,
    emailInputModel: TextInputModel,
    passwordInputModel: TextInputModel,
    passwordRepeatInputModel: TextInputModel,
    isPasswordVisible: Boolean,
    isPasswordRepeatVisible: Boolean,
    isFormValid: Boolean,
    setPasswordVisibility: (Boolean) -> Unit,
    setPasswordRepeatVisibility: (Boolean) -> Unit,
    goToSignIn: () -> Unit,
    submit: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_small),
                        vertical = dimensionResource(R.dimen.padding_xlarge)
                    ),
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

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                UsernameTextField (
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    username = usernameInputModel.value,
                    usernameError = usernameInputModel.errorMessage,
                    onUsernameValueChange = usernameInputModel.onChangeValue
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                EmailTextField(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    email = emailInputModel.value,
                    emailError = emailInputModel.errorMessage,
                    onEmailValueChange = emailInputModel.onChangeValue
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                PasswordTextField(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    password = passwordInputModel.value,
                    passwordError = passwordInputModel.errorMessage,
                    isPasswordVisible = isPasswordVisible,
                    onPasswordValueChange = passwordInputModel.onChangeValue,
                    setPasswordVisibility = setPasswordVisibility
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                PasswordTextField(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                    label = R.string.repeat_password,
                    password = passwordRepeatInputModel.value,
                    passwordError = passwordRepeatInputModel.errorMessage,
                    isPasswordVisible = isPasswordRepeatVisible,
                    onPasswordValueChange = passwordRepeatInputModel.onChangeValue,
                    setPasswordVisibility = setPasswordRepeatVisibility
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                BmFilledButton(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = stringResource(R.string.submit),
                    onClick = submit,
                    enabled = isFormValid
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_large)))

                LoginPrompt(onClick = goToSignIn)
            }
        }
    }
}

@Composable
fun LoginPrompt(
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
            text = stringResource(com.wf.bm.feature.authentication.R.string.already_have_an_account),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(com.wf.bm.feature.authentication.R.string.sign_in),
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
fun RegistrationScreenPreview() {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordRepeatError by remember { mutableStateOf<String?>(null) }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordRepeatVisible by remember { mutableStateOf(false) }

    val usernameInputModel = TextInputModel(
        value = username,
        errorMessage = usernameError,
        onChangeValue = {
            username = it
            usernameError = if (it != "bulatkyr") null else "The username is already taken!"
        }
    )

    val emailInputModel = TextInputModel(
        value = email,
        errorMessage = emailError,
        onChangeValue = {
            email = it
            emailError = if (it.contains("@")) null else "Invalid email!"
        }
    )

    val passwordInputModel = TextInputModel(
        value = password,
        errorMessage = passwordError,
        onChangeValue = {
            password = it
            passwordError = if (it.length < 8) "Minimum 8 characters required" else null
        }
    )

    val passwordRepeatInputModel = TextInputModel(
        value = passwordRepeat,
        errorMessage = passwordRepeatError,
        onChangeValue = {
            passwordRepeat = it
            passwordRepeatError = if (it != password) "Passwords don't match!" else null
        }
    )

    val isFormValid = usernameInputModel.errorMessage == null &&
            emailInputModel.errorMessage == null &&
            passwordInputModel.errorMessage == null &&
            passwordRepeatInputModel.errorMessage == null &&
            username.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty()
    passwordRepeat.isNotEmpty()

    BudgetManagerTheme {
        RegistrationScreen(
            usernameInputModel = usernameInputModel,
            emailInputModel = emailInputModel,
            passwordInputModel = passwordInputModel,
            passwordRepeatInputModel = passwordRepeatInputModel,
            isFormValid = isFormValid,
            isPasswordVisible = isPasswordVisible,
            isPasswordRepeatVisible = isPasswordRepeatVisible,
            setPasswordVisibility = { isPasswordVisible = it },
            setPasswordRepeatVisibility = { isPasswordRepeatVisible = it },
            goToSignIn = {},
            submit = {}
        )
    }
}