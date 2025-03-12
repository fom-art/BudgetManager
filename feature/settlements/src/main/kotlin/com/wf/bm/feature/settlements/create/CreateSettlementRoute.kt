package com.wf.bm.feature.settlements.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.feature.settlements.create.friend_selection.FriendSelectionDialogActions
import com.wf.bm.feature.settlements.create.friend_selection.FriendSelectionDialogViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateSettlementRoute(
    modifier: Modifier = Modifier,
    settlementViewModel: CreateSettlementViewModel = koinViewModel(),
    friendDialogViewModel: FriendSelectionDialogViewModel = koinViewModel(),
    goBack: () -> Unit
) {
    val settlementState = settlementViewModel.state.collectAsStateWithLifecycle().value
    val friendDialogState = friendDialogViewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(settlementState.shouldNavigateBack) {
        if (settlementState.shouldNavigateBack) {
            goBack()
        }
    }

    CreateSettlementScreen(
        modifier = modifier,
        createSettlementState = settlementState,
        createSettlementActions = CreateSettlementActions(
            setIsLoan = settlementViewModel::setIsLoan,
            setAmount = settlementViewModel::setAmount,
            setCurrency = settlementViewModel::setCurrency,
            setDateTime = settlementViewModel::setDateTime,
            setSelectedFriend = settlementViewModel::setSelectedFriend,
            removeSelectedFriend = settlementViewModel::removeSelectedFriend,
            showFriendSelectionDialog = { friendDialogViewModel.setDialogVisibility(true) },
            submit = settlementViewModel::submit
        ),
        friendSelectionDialogState = friendDialogState,
        friendSelectionDialogActions = FriendSelectionDialogActions(
            setSearchQuery = friendDialogViewModel::setSearchQuery,
            onDismissRequest = friendDialogViewModel::dismissDialog,
            selectCustomFriend = settlementViewModel::setSelectedFriend,
            selectFriend = settlementViewModel::setSelectedFriend,
            setDialogVisibility = friendDialogViewModel::setDialogVisibility
        ),
        goBack = goBack
    )
}
