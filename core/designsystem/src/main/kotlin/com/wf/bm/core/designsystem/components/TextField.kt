package com.wf.bm.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wf.bm.core.designsystem.R

@Composable
fun BmTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    error: String? = null,
    maxLength: Int? = null,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    errorStyle: TextStyle = TextStyle(fontSize = 12.sp, color = Color.Red),
    backgroundColor: Color = Color.White,
    borderColor: Color = Color.Gray,
    focusedBorderColor: Color = Color.Blue,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: Shape = RoundedCornerShape(20.dp),  // Customize the corner radius here
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    var isFocused by remember { mutableStateOf(false) }
    val effectiveBorderColor = if (isFocused) focusedBorderColor else borderColor

    // TextField with rounded shape and custom styling
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .border(1.dp, effectiveBorderColor, shape)  // Border with rounded corners
            .onFocusChanged { isFocused = it.isFocused },
        value = value,
        onValueChange = {
            if (maxLength == null || it.length <= maxLength) onValueChange(it)
        },
        textStyle = textStyle,
        singleLine = singleLine,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
    )

    // Error Message
    if (!error.isNullOrEmpty()) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_xsmall)))
        Text(text = error, style = errorStyle)
    }
}

@Composable
fun UsernameTextField(
    modifier: Modifier = Modifier,
    username: String,
    usernameError: String?,
    onUsernameValueChange: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.username),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        BmTextField(
            value = username,
            onValueChange = onUsernameValueChange,
            error = usernameError,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
    }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    emailError: String?,
    onEmailValueChange: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.email),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        BmTextField(
            value = email,
            onValueChange = onEmailValueChange,
            error = emailError,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
    }
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: Int = R.string.password,
    password: String,
    passwordError: String?,
    isPasswordVisible: Boolean,
    onPasswordValueChange: (String) -> Unit,
    setPasswordVisibility: (Boolean) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        BmTextField(
            value = password,
            onValueChange = onPasswordValueChange,
            error = passwordError,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if(!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                val icon =
                    if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (isPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = { setPasswordVisibility(!isPasswordVisible) }) {
                    Icon(imageVector = icon, contentDescription = description)
                }
            },
        )
    }
}


