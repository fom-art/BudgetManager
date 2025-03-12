package com.wf.bm.core.data.database

import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersDatabase(
    private val transactionsDatabase: TransactionsDatabase,
    private val goalsDatabase: GoalsDatabase
) {

    // Internal mutable list of users
    private val _usersFlow = MutableStateFlow(generateUsersWithData())
    val usersFlow: StateFlow<List<User>> = _usersFlow.asStateFlow()

    // Map to store user passwords
    private val userPasswords = generateUserPasswords()

    val users = usersFlow.value

    /**
     * Adds a new user and password to the database and emits the updated list.
     */
    fun addUser(user: User, password: String) {
        val updatedList = _usersFlow.value.toMutableList().apply {
            add(user.copy(
                transactions = transactionsDatabase.getTransactionsForUser(user.id),
                goals = goalsDatabase.getGoalsForUser(user.id)
            ))
        }
        _usersFlow.value = updatedList
        userPasswords[user.id] = password
    }

    /**
     * Removes a user and their password from the database.
     */
    fun removeUser(user: User) {
        val updatedList = _usersFlow.value.toMutableList().apply {
            remove(user)
        }
        _usersFlow.value = updatedList
        userPasswords.remove(user.id)
    }

    /**
     * Updates a user in the list and keeps the password unchanged.
     */
    fun updateUser(updatedUser: User) {
        val updatedList = _usersFlow.value.toMutableList().map {
            if (it.id == updatedUser.id) updatedUser.copy(
                transactions = transactionsDatabase.getTransactionsForUser(updatedUser.id),
                goals = goalsDatabase.getGoalsForUser(updatedUser.id)
            ) else it
        }
        _usersFlow.value = updatedList
    }

    /**
     * Clears all users and passwords from the database.
     */
    fun clearUsers() {
        _usersFlow.value = emptyList()
        userPasswords.clear()
    }

    /**
     * Verifies if the given email and password match a user in the database.
     */
    fun authenticate(email: String, password: String): User? {
        return _usersFlow.value.find { user ->
            user.email == email && userPasswords[user.id] == password
        }
    }

    /**
     * Generates a list of 15 users for the initial database with transactions and goals.
     */
    private fun generateUsersWithData(): List<User> {
        return List(15) { index ->
            val userId = index.toLong()
            val userTransactions = transactionsDatabase.getTransactionsForUser(userId)
            val userGoals = goalsDatabase.getGoalsForUser(userId)

            User(
                id = userId,
                email = "user$index@example.com",
                name = "Name$index",
                surname = "Surname$index",
                nickname = "Nickname$index",
                photo = null,
                debtorSettlements = emptyList(),
                creditorSettlements = emptyList(),
                goals = userGoals,
                transactions = userTransactions,
                friends = emptyList(),
                notifications = emptyList()
            )
        }
    }

    /**
     * Generates a map of user IDs to passwords for the initial users.
     */
    private fun generateUserPasswords(): MutableMap<Long, String> {
        return mutableMapOf<Long, String>().apply {
            (0L until 15L).forEach { id ->
                this[id] = "password$id" // Example: password0, password1, ...
            }
        }
    }
}
