package com.wf.bm.feature.settlements.main.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.wf.bm.core.designsystem.components.CategoryTag
import com.wf.bm.core.designsystem.components.UserListItemSmall
import com.wf.bm.core.model.User
import com.wf.bm.feature.settlements.R

@Composable
fun FilterDialog(
    modifier: Modifier = Modifier,
    filterDialogState: FilterDialogState,
    filterDialogActions: FilterDialogActions
) {
    Dialog(onDismissRequest = filterDialogActions.onDismissRequest) {
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
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.filter_by),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                FilterToggleButtons(
                    modifier = Modifier,
                    isDebtChecked = filterDialogState.isDebtChecked,
                    isLoanChecked = filterDialogState.isLoanChecked,
                    setDebtChecked = filterDialogActions.setDebtChecked,
                    setLoanChecked = filterDialogActions.setLoanChecked,
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(com.wf.bm.core.designsystem.R.string.friends),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                SearchField(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = filterDialogState.searchQuery,
                    onValueChange = filterDialogActions.setSearchQuery
                )

                Spacer(Modifier.height(8.dp))

                SelectedFriendsList(
                    modifier = Modifier
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.1f)
                        .fillMaxWidth(),
                    selectedFriends = filterDialogState.selectedFriends,
                    removeFriend = filterDialogActions.removeFriend
                )

                if (filterDialogState.selectedFriends.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                SuggestedFriendsList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.3f),
                    suggestedFriends = filterDialogState.suggestedFriends,
                    selectedFriends = filterDialogState.selectedFriends,
                    addFriend = filterDialogActions.addFriend,
                    removeFriend = filterDialogActions.removeFriend
                )

                Spacer(modifier = Modifier.height(16.dp))

                DialogActions(
                    modifier = Modifier.fillMaxWidth(),
                    onDismissRequest = filterDialogActions.onDismissRequest,
                    onSubmit = filterDialogActions.submit
                )
            }
        }
    }
}

@Composable
fun FilterToggleButtons(
    modifier: Modifier = Modifier,
    isDebtChecked: Boolean,
    isLoanChecked: Boolean,
    setDebtChecked: (Boolean) -> Unit,
    setLoanChecked: (Boolean) -> Unit,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        CategoryTag(
            title = stringResource(R.string.owed_by_me),
            active = isDebtChecked,
            onClick = { setDebtChecked(!isDebtChecked) }
        )
        CategoryTag(
            title = stringResource(R.string.owed_to_me),
            active = isLoanChecked,
            onClick = { setLoanChecked(!isLoanChecked) }
        )
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
                        contentDescription = stringResource(com.wf.bm.core.designsystem.R.string.clear_search),
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
    suggestedFriends: List<User>,
    selectedFriends: List<User>,
    addFriend: (User) -> Unit,
    removeFriend: (User) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(suggestedFriends) { friend ->
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
                                contentDescription = stringResource(com.wf.bm.core.designsystem.R.string.friend_remove_prompt)
                            )
                        } else {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = stringResource(com.wf.bm.core.designsystem.R.string.add)
                            )
                        }
                    }
                }
            )
        }
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
            Text(stringResource(com.wf.bm.core.designsystem.R.string.cancel))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onSubmit) {
            Text(stringResource(com.wf.bm.core.designsystem.R.string.ok))
        }
    }
}

@Preview
@Composable
fun FilterDialogPreview() {
    BudgetManagerTheme {
        val allFriends = listOf(
            User(1, "John Doe", nickname = "johndoe"),
            User(2, "Jane Doe", nickname = "janedoe"),
            User(3, "Alice Smith", nickname = "alice"),
            User(4, "Bob Brown", nickname = "bobbrown"),
            User(5, "Charlie Johnson", nickname = "charlie"),
            User(6, "David Miller", nickname = ""),
            User(7, "Eve White", nickname = ""),
            User(8, "Frank Black", nickname = "frankb"),
            User(9, "Grace Green", nickname = ""),
            User(10, "Hank Blue", nickname = "hankb")
        )

        val suggestedFriends = remember { mutableStateListOf<User>().apply { addAll(allFriends) } }
        val selectedFriends = remember { mutableStateListOf<User>() }
        var searchQuery by remember { mutableStateOf("") }
        var isDebtChecked by remember { mutableStateOf(true) }
        var isLoanChecked by remember { mutableStateOf(true) }
        var isVisible by remember { mutableStateOf(true) }

        val addFriend: (User) -> Unit = { user ->
            if (!selectedFriends.contains(user)) selectedFriends.add(user)
        }

        val removeFriend: (User) -> Unit = { user ->
            selectedFriends.remove(user)
        }

        val setDebtChecked: (Boolean) -> Unit = { isDebtChecked = it }
        val setLoanChecked: (Boolean) -> Unit = { isLoanChecked = it }
        val setDialogVisibility: (Boolean) -> Unit = { isVisible = it }
        val setSearchQuery: (String) -> Unit = { searchQuery = it }

        val filteredFriends = suggestedFriends.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.nickname.contains(searchQuery, ignoreCase = true)
        }

        BudgetManagerTheme {
            FilterDialog(
                filterDialogState = FilterDialogState(
                    suggestedFriends = filteredFriends,
                    selectedFriends = selectedFriends,
                    searchQuery = searchQuery,
                    isDebtChecked = isDebtChecked,
                    isLoanChecked = isLoanChecked,
                    isVisible = isVisible
                ),
                filterDialogActions = FilterDialogActions(
                    setSearchQuery = setSearchQuery,
                    onDismissRequest = { setDialogVisibility(false) },
                    addFriend = addFriend,
                    removeFriend = removeFriend,
                    submit = {
                        println("Selected Friends: $selectedFriends")
                    },
                    setDialogVisibility = setDialogVisibility,
                    setDebtChecked = setDebtChecked,
                    setLoanChecked = setLoanChecked,
                )
            )
        }
    }
}