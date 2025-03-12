package com.wf.bm.core.data.util

import com.wf.bm.core.data.repository.UserRepository
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface AuthenticationManager {
    val isLoggedIn: Flow<Boolean>
    suspend fun signIn(email: String, password: String): Flow<UiEvent>
    fun logOut()
    val user: User?
    suspend fun  observeCurrentUser(action: (User?) -> Unit)
}

class AuthenticationManagerImpl(private val userRepository: UserRepository) : AuthenticationManager {
    private val _user = MutableStateFlow<User?>(null)
    private val userStateFlow: StateFlow<User?> = _user.asStateFlow()

    override val isLoggedIn: Flow<Boolean> = userStateFlow.map { it != null }.distinctUntilChanged()

    override val user: User?
        get() = _user.value

    override suspend fun signIn(email: String, password: String): Flow<UiEvent> = flow {
        try {
            val authenticatedUser = userRepository.authenticateUser(email, password)
            if (authenticatedUser != null) {
                _user.emit(authenticatedUser)
                emit(UiEvent.Success)
            } else {
                emit(UiEvent.Error("Invalid email or password."))
            }
        } catch (e: Exception) {
            emit(UiEvent.Error("An unexpected error occurred: ${e.message}"))
        }
    }.onStart {
        emit(UiEvent.Loading)
    }.catch { exception ->
        emit(UiEvent.Error("An error occurred: ${exception.message}"))
    }

    override fun logOut() {
        _user.value = null
    }

    override suspend fun observeCurrentUser(action: (User?) -> Unit) {
        userStateFlow.collectLatest { user ->
            action(user)
        }
    }
}
