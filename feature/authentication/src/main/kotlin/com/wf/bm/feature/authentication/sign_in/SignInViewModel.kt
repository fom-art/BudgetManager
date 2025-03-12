package com.wf.bm.feature.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.data.util.UiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Error

class SignInViewModel(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _signInState = MutableStateFlow(SignInState())
    val state = _signInState.asStateFlow()

    fun updateEmail(email: String) {
        val errorMessage = if (email.contains("@")) null else "Invalid email!"
        _signInState.update {
            it.copy(
                email = email,
                emailError = errorMessage,
                isFormValid = isFormValid(emailError = errorMessage, passwordError = it.passwordError)
            )
        }
    }

    fun updatePassword(password: String) {
        val errorMessage = if (password.length >= 8) null else "Minimum 8 characters required"
        _signInState.update {
            it.copy(
                password = password,
                passwordError = errorMessage,
                isFormValid = isFormValid(emailError = it.emailError, passwordError = errorMessage)
            )
        }
    }

    fun togglePasswordVisibility() {
        _signInState.update {
            it.copy(
                isPasswordVisible = !_signInState.value.isPasswordVisible
            )
        }
    }

    fun signIn() {
        viewModelScope.launch {
            authenticationManager.signIn(
                email = state.value.email,
                password = state.value.password
            ).collect { result ->
                if (result is UiEvent.Error) {
                    logSignInFailure(result)
                }
            }
        }
    }

    private fun logSignInFailure(result: UiEvent.Error) {
        _signInState.update {
            it.copy(
                isSignInFailed = true,
                signInError = result.message
            )
        }
    }

    /**
     * Determines if the form is valid by checking if there are no error messages.
     */
    private fun isFormValid(emailError: String?, passwordError: String?): Boolean {
        return emailError == null && passwordError == null
    }

    data class SignInState(
        val email: String = "",
        val emailError: String? = null,
        val password: String = "",
        val passwordError: String? = null,
        val isPasswordVisible: Boolean = false,
        val isFormValid: Boolean = false, // Default to false until validated
        val isSignInFailed: Boolean = false,
        val signInError: String? = null
    )
}