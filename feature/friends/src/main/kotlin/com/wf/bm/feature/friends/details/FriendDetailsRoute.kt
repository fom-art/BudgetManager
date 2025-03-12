package com.wf.bm.feature.friends.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun FriendDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: FriendDetailsViewModel = koinViewModel(),
    friend: User,
    goBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    viewModel.setFriend(friend)

    LaunchedEffect(state.shouldNavigateBack) {
        if (state.shouldNavigateBack) {
            goBack()
        }
    }

    FriendDetailsScreen(
        modifier = modifier,
        user = state.friend,
        totalDebt = state.totalDebt,
        totalDebtCurrency = state.totalDebtCurrency,
        closeTotalDebt = viewModel::closeTotalDebt,
        deleteFriend = viewModel::deleteFriend,
        goBack = goBack
    )
}
