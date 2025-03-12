package com.wf.bm.feature.transactions.create.split.friends_selection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.UserListItemSmall
import com.wf.bm.core.model.User
import com.wf.bm.core.designsystem.components.CategoryTag

@Composable
fun FriendsSelectionDialog(
    modifier: Modifier = Modifier,
    title: String,
    friendsSelectionDialogState: FriendsSelectionDialogState,
    friendsSelectionDialogActions: FriendsSelectionDialogActions
) {
    var customFriendName by remember { mutableStateOf("") }

    Dialog(onDismissRequest = friendsSelectionDialogActions.onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp,
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                // Search Field
                SearchField(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = friendsSelectionDialogState.searchQuery,
                    onValueChange = friendsSelectionDialogActions.setSearchQuery
                )

                Spacer(Modifier.height(8.dp))

                // Selected Friends
                SelectedFriendsList(
                    modifier = Modifier
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
                        .fillMaxWidth(),
                    selectedFriends = friendsSelectionDialogState.selectedFriends,
                    removeFriend = friendsSelectionDialogActions.removeFriend
                )

                if (friendsSelectionDialogState.selectedFriends.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Suggested Friends
                SuggestedFriendsList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.3f),
                    friends = friendsSelectionDialogState.friends,
                    selectedFriends = friendsSelectionDialogState.selectedFriends,
                    addFriend = friendsSelectionDialogActions.addFriend,
                    removeFriend = friendsSelectionDialogActions.removeFriend
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Custom Friend Input
                CustomFriendInput(
                    modifier = Modifier.fillMaxWidth(),
                    customFriendName = customFriendName,
                    onCustomFriendNameChange = { customFriendName = it },
                    onAddCustomFriend = {
                        if (customFriendName.isNotEmpty()) {
                            friendsSelectionDialogActions.addCustomFriend(User(name = customFriendName))
                            customFriendName = ""
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                DialogActions(
                    modifier = Modifier.fillMaxWidth(),
                    onDismissRequest = friendsSelectionDialogActions.onDismissRequest,
                    onSubmit = friendsSelectionDialogActions.submit
                )
            }
        }
    }
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = onValueChange,
        placeholder = { Text("Search") },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.clear_search),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
        )
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectedFriendsList(
    modifier: Modifier = Modifier,
    selectedFriends: List<User>,
    removeFriend: (User) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            FlowRow(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                selectedFriends.forEach { friend ->
                    val title = friend.name + " " + friend.surname
                    CategoryTag(title = title, onClick = { removeFriend(friend) })
                }
            }
        }
    }
}

@Composable
fun SuggestedFriendsList(
    modifier: Modifier = Modifier,
    friends: List<User>,
    selectedFriends: List<User>,
    addFriend: (User) -> Unit,
    removeFriend: (User) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(friends) { friend ->
            UserListItemSmall(
                user = friend,
                trailingContent = {
                    IconButton(onClick = {
                        if (selectedFriends.contains(friend)) {
                            removeFriend(friend)
                        } else {
                            addFriend(friend)
                        }
                    }) {
                        if (selectedFriends.contains(friend)) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = stringResource(R.string.friend_remove_prompt)
                            )
                        } else {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = stringResource(R.string.add)
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CustomFriendInput(
    modifier: Modifier = Modifier,
    customFriendName: String,
    onCustomFriendNameChange: (String) -> Unit,
    onAddCustomFriend: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = onAddCustomFriend) {
            Text("Add")
        }
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = customFriendName,
            onValueChange = onCustomFriendNameChange,
            placeholder = { Text("Custom Friend") },
            singleLine = true,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )
    }
}

@Composable
fun DialogActions(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSubmit: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onDismissRequest) {
            Text(stringResource(R.string.cancel))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onSubmit) {
            Text(stringResource(R.string.ok))
        }
    }
}

@Preview
@Composable
fun SplitDialogPreview() {
    BudgetManagerTheme {
        // Database of friends
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

        // States for the friends lists and search query
        val suggestedFriends = remember { mutableStateListOf<User>() }
        val selectedFriends = remember { mutableStateListOf<User>() }
        var searchQuery by remember { mutableStateOf("") }

        // Initialize suggested friends with all friends
        LaunchedEffect(Unit) {
            suggestedFriends.clear()
            suggestedFriends.addAll(allFriends)
        }

        // Function to add a friend
        val addFriend: (User) -> Unit = { user ->
            if (!selectedFriends.contains(user)) {
                selectedFriends.add(user)
            }
        }

        // Function to remove a friend
        val removeFriend: (User) -> Unit = { user ->
            selectedFriends.remove(user)
        }

        // Function to add a custom friend
        val addCustomFriend: (User) -> Unit = { user ->
            if (!suggestedFriends.contains(user)) {
                suggestedFriends.add(user)
                selectedFriends.add(user)
            }
        }

        // Filtered friends based on search query
        val filteredFriends = suggestedFriends.filter { user ->
            user.name.contains(searchQuery, ignoreCase = true) ||
                    user.nickname.contains(searchQuery, ignoreCase = true)
        }

        // Render the SplitDialog
        BudgetManagerTheme {
            FriendsSelectionDialog(
                title = "Friend selection dialog",
                friendsSelectionDialogState = FriendsSelectionDialogState(
                    friends = suggestedFriends,
                    selectedFriends = selectedFriends,
                    searchQuery = searchQuery
                ),
                friendsSelectionDialogActions = FriendsSelectionDialogActions(setSearchQuery = { searchQuery = it },
                    onDismissRequest = {},
                    addCustomFriend = addCustomFriend,
                    addFriend = addFriend,
                    removeFriend = removeFriend,
                    submit = {
                        println("Selected Friends: $selectedFriends")
                    },
                    setDialogVisibility = {}
                ),
            )
        }
    }
}
