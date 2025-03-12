package com.wf.bm.feature.transactions.create

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogActions
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateTransactionBottomSheetDialog(
    modifier: Modifier = Modifier,
    createTransactionViewModel: CreateTransactionViewModel = koinViewModel(),
    friendsSelectionDialogViewModel: FriendsSelectionDialogViewModel = koinViewModel(),
    dismiss: () -> Unit
) {
    val transactionState = createTransactionViewModel.state.collectAsStateWithLifecycle().value
    val friendsDialogState =
        friendsSelectionDialogViewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(transactionState.shouldNavigateBack) {
        Log.d("CreateTransactionBottomSheetDialog", "shouldNavigateBack ${transactionState.shouldNavigateBack}")
        if (transactionState.shouldNavigateBack) {
            dismiss()
            createTransactionViewModel.resetState()
            friendsSelectionDialogViewModel.resetState()
        }
    }

    CreateTransactionBottomSheetDialog(
        modifier = modifier,
        transaction = transactionState.transaction,
        updateTransaction = createTransactionViewModel::updateTransaction,
        updateUserSplit = createTransactionViewModel::updateUserSplit,
        split = transactionState.split,
        removeSplit = createTransactionViewModel::removeSplit,
        isSplitEvenly = transactionState.isSplitEvenly,
        setSplitEvenly = createTransactionViewModel::setSplitEvenly,
        friendsSelectionDialogState = friendsDialogState,
        friendsSelectionDialogActions = FriendsSelectionDialogActions(
            setSearchQuery = friendsSelectionDialogViewModel::setSearchQuery,
            onDismissRequest = { friendsSelectionDialogViewModel.setDialogVisibility(false) },
            addCustomFriend = { friendsSelectionDialogViewModel.addFriend(it) },
            addFriend = { friendsSelectionDialogViewModel.addFriend(it) },
            removeFriend = { friendsSelectionDialogViewModel.removeFriend(it) },
            submit = { friendsSelectionDialogViewModel.setDialogVisibility(false) },
            setDialogVisibility = friendsSelectionDialogViewModel::setDialogVisibility
        ),
        dismiss = {
            dismiss()
            createTransactionViewModel.resetState()
            friendsSelectionDialogViewModel.resetState()
        },
        submit = createTransactionViewModel::submit
    )
}
