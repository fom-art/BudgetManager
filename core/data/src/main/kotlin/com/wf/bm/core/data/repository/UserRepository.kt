package com.wf.bm.core.data.repository

import com.wf.bm.core.data.util.UiEvent
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {
    fun authenticateUser(email: String, password: String): User?
    suspend fun findUsersByNickname(nickname: String): List<User>
    fun findUsersByFullName(fullName: String): List<User>
    fun findUserByEmail(email: String): User?
    fun findUserByUsername(username: String): User?
    fun uploadAvatarForUser(user: User, avatar: File)
    fun sendPasswordResetEmailToUser(user: User)
    suspend fun registerUser(user: User, password: String): Flow<UiEvent>
    suspend fun addFriendToUser(user: User, friend: User)
    suspend fun removeFriendFromUser(user: User, friend: User)
    suspend fun closeSettlementsBetweenUsers(user1: User, user2: User)
}

