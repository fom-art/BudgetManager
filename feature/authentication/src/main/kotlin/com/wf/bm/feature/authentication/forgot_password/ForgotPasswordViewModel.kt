package com.wf.bm.feature.authentication.forgot_password

import androidx.lifecycle.ViewModel
import com.wf.bm.core.data.repository.UserRepository
import com.wf.bm.core.model.TextInputModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotPasswordViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state = _state.asStateFlow()

    fun updateEmail(email: String) {
        _state.update {
            it.copy(
                emailInputModel = it.emailInputModel.copy(value = email)
            )
        }
        if (email.contains("@") && email.isNotEmpty()) {
            removeErrorMessage()
        } else {
            setErrorMessage("Invalid email!")
        }
    }

    fun confirmEmail() {
        val email = _state.value.emailInputModel.value
        val user = userRepository.findUserByEmail(email)
        if (user == null) {
            setErrorMessage("User with email was not found")
        } else {
            userRepository.sendPasswordResetEmailToUser(user)
            _state.update {
                it.copy(
                    shouldGoToEmailSent = true
                )
            }
        }
    }

    private fun setErrorMessage(errorMessage: String) {
        _state.update {
            it.copy(
                emailInputModel = _state.value.emailInputModel.copy(
                    errorMessage = errorMessage
                ),
                isFormValid = false
            )
        }
    }

    private fun removeErrorMessage() {
        _state.update {
            it.copy(
                emailInputModel = _state.value.emailInputModel.copy(
                    errorMessage = null
                ),
                isFormValid = true
            )
        }
    }

    data class ForgotPasswordState(
        val emailInputModel: TextInputModel = TextInputModel(
            value = "",
            errorMessage = null,
            onChangeValue = {}
        ),
        val shouldGoToEmailSent: Boolean = false,
        val isFormValid: Boolean = false
    )
}
