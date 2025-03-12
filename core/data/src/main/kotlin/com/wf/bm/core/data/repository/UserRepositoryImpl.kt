package com.wf.bm.core.data.repository

import com.wf.bm.core.data.database.UsersDatabase
import com.wf.bm.core.data.util.UiEvent
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class UserRepositoryImpl(private val usersDatabase: UsersDatabase) : UserRepository {

    override fun authenticateUser(email: String, password: String): User? {
        return usersDatabase.authenticate(email, password)
    }

    override suspend fun findUsersByNickname(nickname: String): List<User> {
        return usersDatabase.usersFlow.value.filter { user ->
            user.nickname.contains(nickname, ignoreCase = true)
        }
    }

    override fun findUsersByFullName(fullName: String): List<User> {
        val (name, surname) = fullName.split(" ", limit = 2).let {
            it.getOrElse(0) { "" } to it.getOrElse(1) { "" }
        }
        return usersDatabase.usersFlow.value.filter { user ->
            user.name.equals(name, ignoreCase = true) &&
                    user.surname.equals(surname, ignoreCase = true)
        }
    }

    override fun findUserByEmail(email: String): User? {
        return usersDatabase.usersFlow.value.find { user ->
            user.email.equals(email, ignoreCase = true)
        }
    }

    override fun findUserByUsername(username: String): User? {
        return usersDatabase.usersFlow.value.find { user ->
            user.nickname.equals(username, ignoreCase = true)
        }
    }

    override fun uploadAvatarForUser(user: User, avatar: File) {
        val updatedUser = user.copy(photo = avatar.absolutePath)
        usersDatabase.updateUser(updatedUser)
    }

    override fun sendPasswordResetEmailToUser(user: User) {
        println("Password reset email sent to ${user.email}") // Mock implementation
    }

    override suspend fun registerUser(user: User, password: String): Flow<UiEvent> = flow {
        val existingUser = usersDatabase.usersFlow.value.find { it.email == user.email }
        if (existingUser != null) {
            emit(UiEvent.Error("User with this email already exists"))
        } else {
            usersDatabase.addUser(user, password)
            emit(UiEvent.Success)
        }
    }

    override suspend fun addFriendToUser(user: User, friend: User) {
        val updatedUser = user.copy(friends = user.friends + friend)
        usersDatabase.updateUser(updatedUser)
    }

    override suspend fun removeFriendFromUser(user: User, friend: User) {
        val updatedUser = user.copy(friends = user.friends - friend)
        usersDatabase.updateUser(updatedUser)
    }

    override suspend fun closeSettlementsBetweenUsers(user1: User, user2: User) {
        val updatedUser1 = user1.copy(
            debtorSettlements = user1.debtorSettlements.filter { it.creditor != user2 },
            creditorSettlements = user1.creditorSettlements.filter { it.debtor != user2 }
        )
        val updatedUser2 = user2.copy(
            debtorSettlements = user2.debtorSettlements.filter { it.creditor != user1 },
            creditorSettlements = user2.creditorSettlements.filter { it.debtor != user1 }
        )
        usersDatabase.updateUser(updatedUser1)
        usersDatabase.updateUser(updatedUser2)
    }
}
