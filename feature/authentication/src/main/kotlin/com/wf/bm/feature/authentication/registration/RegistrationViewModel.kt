package com.wf.bm.feature.authentication.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.repository.UserRepository
import com.wf.bm.core.data.util.UiEvent
import com.wf.bm.core.model.TextInputModel
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _navigateToRegistrationComplete = MutableStateFlow(false)
    val navigateToRegistrationComplete = _navigateToRegistrationComplete.asStateFlow()

    fun updateUsername(username: String) {
        val errorMessage = if (userRepository.findUserByUsername(username) != null) {
            "The username is already taken!"
        } else {
            null
        }

        _state.update {
            it.copy(
                usernameInputModel = _state.value.usernameInputModel.copy(
                    value = username,
                    errorMessage = errorMessage
                ),
                isFormValid = validateForm(_state.value)
            )
        }
    }


    fun updateEmail(email: String) {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        val errorMessage = when {
            !emailRegex.matches(email) -> "Invalid email!"
            userRepository.findUserByEmail(email) != null -> "The email is already in use!"
            else -> null
        }

        _state.update {
            it.copy(
                emailInputModel = _state.value.emailInputModel.copy(
                    value = email,
                    errorMessage = errorMessage
                ),
                isFormValid = validateForm(_state.value)
            )
        }
    }


    fun updatePassword(password: String) {
        val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$".toRegex()
        val errorMessage = if (!passwordRegex.matches(password)) {
            "Password must be at least 8 characters long and include both letters and numbers."
        } else {
            null
        }

        _state.update {
            it.copy(
                passwordInputModel = _state.value.passwordInputModel.copy(
                    value = password,
                    errorMessage = errorMessage
                ),
                isFormValid = validateForm(_state.value)
            )
        }
    }

    fun updatePasswordRepeat(passwordRepeat: String) {
        val errorMessage = if (passwordRepeat == _state.value.passwordInputModel.value) {
            null
        } else {
            "Passwords don't match!"
        }

        _state.update {
            it.copy(
                passwordRepeatInputModel = _state.value.passwordRepeatInputModel.copy(
                    value = passwordRepeat,
                    errorMessage = errorMessage
                ),
                isFormValid = validateForm(_state.value)
            )
        }
    }

    fun togglePasswordVisibility(passwordVisibility: Boolean) {
        _state.update {
            it.copy(isPasswordVisible = passwordVisibility)
        }
    }

    fun togglePasswordRepeatVisibility(passwordRepeatVisibility: Boolean) {
        _state.update {
            it.copy(isPasswordRepeatVisible = passwordRepeatVisibility)
        }
    }

    private fun validateForm(state: RegistrationState): Boolean {
        return state.usernameInputModel.errorMessage == null &&
                state.emailInputModel.errorMessage == null &&
                state.passwordInputModel.errorMessage == null &&
                state.passwordRepeatInputModel.errorMessage == null &&
                state.usernameInputModel.value.isNotEmpty() &&
                state.emailInputModel.value.isNotEmpty() &&
                state.passwordInputModel.value.isNotEmpty() &&
                state.passwordRepeatInputModel.value.isNotEmpty()
    }

    fun submit() {
        val currentState = _state.value

        // Validate the form before proceeding
        if (!currentState.isFormValid) {
            handleFormInvalid()
            return
        }

        viewModelScope.launch {
            userRepository.registerUser(
                User(
                    email = currentState.emailInputModel.value,
                    nickname = currentState.usernameInputModel.value,
                ),
                password = currentState.passwordInputModel.value
            ).collect { uiEvent -> handleUiEvent(uiEvent) }
        }
    }

    private fun handleFormInvalid() {
        Log.e(this::class.simpleName, "Form is invalid. Cannot proceed with registration.")
        _state.update {
            it.copy(
                emailInputModel = it.emailInputModel.copy(
                    errorMessage = "Please correct the errors before submitting."
                )
            )
        }
    }

    private fun handleUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Error -> handleRegistrationError(uiEvent.message)
            UiEvent.Loading -> handleLoadingState()
            is UiEvent.Success -> handleRegistrationSuccess()
        }
    }

    private fun handleRegistrationError(message: String) {
        Log.e(this::class.simpleName, "Registration failed: $message")
        _state.update {
            it.copy(
                emailInputModel = it.emailInputModel.copy(
                    errorMessage = "Registration failed: $message"
                )
            )
        }
    }

    private fun handleLoadingState() {
        Log.d(this::class.simpleName, "Registration is in progress...")
    }

    private fun handleRegistrationSuccess() {
        Log.d(this::class.simpleName, "Registration succeeded!")
        navigateToRegistrationComplete()
    }

    private fun navigateToRegistrationComplete() {
        _navigateToRegistrationComplete.value = true
    }

    data class RegistrationState(
        val usernameInputModel: TextInputModel = TextInputModel(
            value = "",
            errorMessage = null,
            onChangeValue = {}),
        val emailInputModel: TextInputModel = TextInputModel(
            value = "",
            errorMessage = null,
            onChangeValue = {}),
        val passwordInputModel: TextInputModel = TextInputModel(
            value = "",
            errorMessage = null,
            onChangeValue = {}),
        val passwordRepeatInputModel: TextInputModel = TextInputModel(
            value = "",
            errorMessage = null,
            onChangeValue = {}),
        val isPasswordVisible: Boolean = false,
        val isPasswordRepeatVisible: Boolean = false,
        val isFormValid: Boolean = false
    )
}
