package com.wf.bm.feature.friends.main

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.SearchBarComponent
import com.wf.bm.core.designsystem.components.UserDismissibleItem
import com.wf.bm.core.designsystem.components.UserListItem
import com.wf.bm.core.model.User
import com.wf.bm.feature.friends.R

@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    friends: List<User>,
    suggestedUsers: List<User>,
    textFieldState: TextFieldState,
    isSearchBarExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    openFriendDetails: (User) -> Unit,
    deleteFriend: (User) -> Unit,
) {
    val animatedSearchBarHorizontalPadding by animateDpAsState(
        targetValue = if (!isSearchBarExpanded) {
            dimensionResource(com.wf.bm.core.designsystem.R.dimen.padding_medium)
        } else {
            0.dp
        }, label = ""
    )
    val animatedSearchBarVerticalPadding by animateDpAsState(
        targetValue = if (!isSearchBarExpanded) {
            dimensionResource(com.wf.bm.core.designsystem.R.dimen.padding_small)
        } else {
            0.dp
        }, label = ""
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchBarComponent(modifier = Modifier.padding(horizontal = animatedSearchBarHorizontalPadding, vertical = animatedSearchBarVerticalPadding),
            textFieldState = textFieldState,
            expanded = isSearchBarExpanded,
            onExpandedChange = onExpandedChange,
            placeHolder = stringResource(R.string.nick_name_example),
            clearSearch = { textFieldState.setTextAndPlaceCursorAtEnd("") },
            content = {
                LazyColumn(modifier = Modifier.padding().fillMaxSize()) {
                    items(suggestedUsers) { suggestedFriend ->
                        UserListItem(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable { openFriendDetails(suggestedFriend) },
                            user = suggestedFriend,
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(dimensionResource(com.wf.bm.core.designsystem.R.dimen.padding_medium)))

        LazyColumn(modifier = Modifier.padding()) {
            items(friends) { friend ->
                UserDismissibleItem(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { openFriendDetails(friend) },
                    user = friend,
                    onRemove = deleteFriend
                )
            }
        }
    }
}

@Preview
@Composable
fun FriendsMainScreenPreview() {
    val friends = remember {
        mutableStateListOf(
            User(
                id = 0,
                name = "John",
                surname = "Doe",
                nickname = "johndoe",
                photo = ""
            ),
            User(
                id = 1,
                name = "Jane",
                surname = "Smith",
                nickname = "janesmith",
                photo = ""
            ),
            User(
                id = 2,
                name = "Bob",
                surname = "Brown",
                nickname = "bobby",
                photo = ""
            ),
            User(
                id = 3,
                name = "Alice",
                surname = "Johnson",
                nickname = "alicej",
                photo = ""
            )
        )
    }

    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    val suggestedFriends by remember {
        derivedStateOf {
            friends.filter { it.nickname.contains(textFieldState.text, ignoreCase = true) }
        }
    }

    // Compose preview UI
    BudgetManagerTheme {
        FriendsScreen(
            friends = friends,
            deleteFriend = { friend -> friends.remove(friend) },
            openFriendDetails = {},
            textFieldState = textFieldState,
            suggestedUsers = suggestedFriends,
            isSearchBarExpanded = expanded,
            onExpandedChange = { expanded = !expanded }
        )
    }
}
