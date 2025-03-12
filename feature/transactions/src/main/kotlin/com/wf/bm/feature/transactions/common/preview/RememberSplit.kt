package com.wf.bm.feature.transactions.common.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.wf.bm.core.model.User
import com.wf.bm.core.utils.toStringWithTwoDecimalsAndNoTrailingZeros
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogActions
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogState

@Composable
fun rememberSplitState(transactionAmount: Double): Pair<SplitState, SplitActions> {
    // Initial data
    val initialSplit: Map<User, String>? = null
    val allFriends = listOf(
        User(1, "John Doe", nickname = "johndoe"),
        User(2, "Jane Doe", nickname = "janedoe"),
        User(3, "Alice Smith", nickname = "alice"),
        User(4, "Bob Brown", nickname = "bobbrown"),
        User(5, "Charlie Johnson", nickname = "charlie"),
        User(6, "David Miller", nickname = "davidm"),
        User(7, "Eve White", nickname = "evew"),
        User(8, "Frank Black", nickname = "frankb"),
        User(9, "Grace Green", nickname = "graceg"),
        User(10, "Hank Blue", nickname = "hankb")
    )

    // State management
    var splitData by remember { mutableStateOf(initialSplit) }
    var isSplitEvenly by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    val friends = remember { mutableStateListOf<User>() }
    val selectedFriends = remember { mutableStateListOf<User>() }
    var searchQuery by remember { mutableStateOf("") }

    val friendsSelectionDialogState = FriendsSelectionDialogState(
        friends = friends,
        selectedFriends = selectedFriends,
        searchQuery = searchQuery,
        isVisible = isVisible
    )

    // Initialize suggested friends
    LaunchedEffect(Unit) {
        friends.clear()
        friends.addAll(allFriends)
    }

    // Actions
    val updateUserSplit: (User, String) -> Unit = { user, newAmount ->
        // Validate the newAmount as a proper number or allow empty fields
        if (newAmount.isEmpty() || newAmount.matches(Regex("^-?(0|[1-9]\\d*)(\\.\\d+)?\$")) || newAmount == "0." || newAmount.endsWith(".")) {
            splitData = splitData?.let {
                it.toMutableMap().apply {
                    this[user] = newAmount
                }
            }
            println("Updated split for ${user.name}: $newAmount")
        } else {
            println("Invalid amount entered for ${user.name}: $newAmount")
        }
    }

    val removeSplit: () -> Unit = {
        splitData = null
        println("Split removed")
    }

    val addFriend: (User) -> Unit = { user ->
        if (!selectedFriends.contains(user)) {
            selectedFriends.add(user)
        }
    }

    val removeFriend: (User) -> Unit = { user ->
        selectedFriends.remove(user)
    }

    val addCustomFriend: (User) -> Unit = { user ->
        if (!friends.contains(user)) {
            friends.add(user)
            selectedFriends.add(user)
        }
    }

    val setSearchQuery: (String) -> Unit = { query ->
        searchQuery = query
    }

    val onDismissRequest: () -> Unit = {
        println("Dialog dismissed")
        isVisible = false
    }

    val submit: () -> Unit = {
        val currentSplit = (splitData ?: emptyMap()).toMutableMap()

        // Remove any user not in `selectedFriends`
        currentSplit.keys.retainAll(selectedFriends)

        // Add newly selected friends (if missing)
        selectedFriends.forEach { friend ->
            if (!currentSplit.containsKey(friend)) {
                currentSplit[friend] = 0.0.toStringWithTwoDecimalsAndNoTrailingZeros()  // Default
            }
        }

        // If we are splitting evenly, recalculate
        if (isSplitEvenly && currentSplit.isNotEmpty()) {
            val evenAmount = transactionAmount / (currentSplit.size + 1)

            // Assign the even amount to each friend
            currentSplit.keys.forEach { friend ->
                currentSplit[friend] = evenAmount.toStringWithTwoDecimalsAndNoTrailingZeros()
            }
        }

        // Assign back to trigger recomposition
        splitData = currentSplit

        println("Updated splitData after submission: $splitData")
        isVisible = false
    }

    val setDialogVisibility: (Boolean) -> Unit = { visible ->
        isVisible = visible
        println("Dialog visibility set to: $visible")
    }

    val setSplitEvenly: (Boolean) -> Unit = { splitEvenly ->
        val currentSplit = splitData ?: emptyMap()

        if (splitEvenly && currentSplit.isNotEmpty()) {
            val evenAmount = transactionAmount / (currentSplit.size + 1)

            splitData =
                currentSplit.mapValues { evenAmount.toStringWithTwoDecimalsAndNoTrailingZeros() }
        }

        // However your code tracks the 'splitEvenly' state
        isSplitEvenly = splitEvenly
    }

    return SplitState(
        splitData = splitData,
        isSplitEvenly = isSplitEvenly,
        friendsSelectionDialogState = friendsSelectionDialogState
    ) to SplitActions(
        updateUserSplit = updateUserSplit,
        removeSplit = removeSplit,
        setSplitEvenly = setSplitEvenly,
        friendsSelectionDialogActions = FriendsSelectionDialogActions(
            setSearchQuery = setSearchQuery,
            onDismissRequest = onDismissRequest,
            addCustomFriend = addCustomFriend,
            addFriend = addFriend,
            removeFriend = removeFriend,
            submit = submit,
            setDialogVisibility = setDialogVisibility
        )
    )
}