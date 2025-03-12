package com.wf.bm.feature.friends.main

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun FriendsRoute(
    modifier: Modifier = Modifier,
    viewModel: FriendsViewModel = koinViewModel(),
    openFriendDetails: (User) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val textFieldState = remember { androidx.compose.foundation.text.input.TextFieldState() }

    FriendsScreen(
        modifier = modifier,
        friends = state.friends,
        suggestedUsers = state.suggestedUsers,
        textFieldState = textFieldState,
        isSearchBarExpanded = textFieldState.text.isNotEmpty(),
        onExpandedChange = { expanded -> if (!expanded) textFieldState.setTextAndPlaceCursorAtEnd("") },
        openFriendDetails = openFriendDetails,
        deleteFriend = { friend -> viewModel.deleteFriend(friend) }
    )
}