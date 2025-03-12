package com.wf.bm.feature.authentication.forgot_password

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.wf.bm.core.designsystem.components.buttons.BmOutlinedButton
import com.wf.bm.core.designsystem.components.EmailTextField
import com.wf.bm.core.designsystem.values.paddingLarge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.designsystem.values.paddingSmall
import com.wf.bm.core.designsystem.values.paddingXLarge
import com.wf.bm.core.designsystem.values.paddingXSmall
import com.wf.bm.core.model.TextInputModel
import com.wf.bm.feature.authentication.sign_in.RegistrationPrompt

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    emailInputModel: TextInputModel,
    isFormValid: Boolean,
    goToEmailSent: () -> Unit,
    goToSignIn: () -> Unit,
    confirmEmail: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedGradientBackground()

        ForgotPasswordForm(
            modifier = modifier,
            emailInputModel = emailInputModel,
            isFormValid = isFormValid,
            goToEmailSent = goToEmailSent,
            goToSignIn = goToSignIn,
            confirmEmail = confirmEmail
        )
    }
}

@Composable
fun ForgotPasswordForm(
    modifier: Modifier = Modifier,
    emailInputModel: TextInputModel,
    isFormValid: Boolean,
    goToEmailSent: () -> Unit,
    goToSignIn: () -> Unit,
    confirmEmail: () -> Unit,
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
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(com.wf.bm.feature.authentication.R.string.reset_password),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(paddingMedium))

                EmailTextField (
                    modifier = Modifier.padding(paddingSmall),
                    email = emailInputModel.value,
                    emailError = emailInputModel.errorMessage,
                    onEmailValueChange = emailInputModel.onChangeValue
                )

                Spacer(modifier = Modifier.height(paddingMedium))

                Row (modifier = Modifier.padding(horizontal = paddingSmall)){
                    Spacer(Modifier.weight(1f))

                    Spacer(modifier = Modifier.width(paddingMedium))

                    BmFilledButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.confirm),
                        onClick = confirmEmail,
                        enabled = isFormValid
                    )
                }

                Spacer(modifier = Modifier.height(paddingLarge))

                RegistrationPrompt(onClick = goToEmailSent)

                Spacer(modifier = Modifier.height(paddingMedium))

                SignInPrompt(onClick = goToSignIn)
            }
        }
    }
}

@Composable
fun SignInPrompt(
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
            text = stringResource(com.wf.bm.feature.authentication.R.string.go_back_to_sign_in),
            style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.Underline),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = paddingXSmall)
                .clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var isFormValid by remember { mutableStateOf(false) }

    val emailInputModel = TextInputModel(
        value = email,
        errorMessage = emailError,
        onChangeValue = { newEmail ->
            email = newEmail
            emailError = if (newEmail.contains("@")) null else "Invalid email!"
        }
    )

    LaunchedEffect(emailError) {
        isFormValid = emailError == null && email.isNotEmpty()
    }

    BudgetManagerTheme {
        ForgotPasswordScreen(
            emailInputModel = emailInputModel,
            isFormValid = isFormValid,
            goToEmailSent = {},
            goToSignIn = {},
            confirmEmail = {}
        )
    }
}