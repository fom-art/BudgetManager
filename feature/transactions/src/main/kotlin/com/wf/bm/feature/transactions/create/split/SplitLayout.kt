package com.wf.bm.feature.transactions.create.split

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.UserListItemSmall
import com.wf.bm.core.designsystem.components.buttons.AnimatedToggleButton
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialog
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogActions
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogState
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.designsystem.values.paddingSmall
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import com.wf.bm.core.utils.toStringWithTwoDecimalsAndNoTrailingZeros
import com.wf.bm.feature.transactions.R
import com.wf.bm.feature.transactions.common.preview.rememberSplitState
import com.wf.bm.feature.transactions.common.preview.rememberTransactionState

@Composable
fun SplitLayout(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    setSplitEvenly: (Boolean) -> Unit,
    changeLoanForUser: (User, String) -> Unit,
    removeSplit: () -> Unit,
    split: Map<User, String>?,
    isSplitEvenly: Boolean,
    friendsSelectionDialogState: FriendsSelectionDialogState,
    friendsSelectionDialogActions: FriendsSelectionDialogActions
) {
    val isSplit = split != null
    Column(modifier = modifier.animateContentSize()) {
        Row {
            AnimatedToggleButton(
                text = stringResource(R.string.split),
                isChecked = isSplit,
                onCheckedChange = {
                    if (isSplit) {
                        removeSplit()
                    } else {
                        friendsSelectionDialogActions.setDialogVisibility(true)
                    }
                }
            )
            AnimatedVisibility(visible = isSplit) {
                OutlinedButton(
                    onClick = { friendsSelectionDialogActions.setDialogVisibility(true) },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(stringResource(R.string.edit_split))
                }
            }
        }

        AnimatedVisibility(visible = isSplit) {
            Column {
                Spacer(Modifier.height(paddingMedium))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isSplitEvenly,
                        onCheckedChange = setSplitEvenly
                    )
                    Spacer(Modifier.width(paddingSmall))
                    Text(
                        text = stringResource(R.string.split_evenly),
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (isSplitEvenly) MaterialTheme.colorScheme.onBackground
                        else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Spacer(Modifier.height(paddingMedium))

                // Display the list of split users if it’s not null
                split?.let { safeSplit ->
                    SplitUserListLayout(
                        currency = transaction.currency,
                        transactionAmount = transaction.amount,
                        isSplitEvenly = isSplitEvenly,
                        split = safeSplit,
                        changeLoanForUser = changeLoanForUser
                    )
                }
            }
        }

    }

    if (friendsSelectionDialogState.isVisible) {
        FriendsSelectionDialog(
            title = stringResource(R.string.split_set_up),
            friendsSelectionDialogState = friendsSelectionDialogState,
            friendsSelectionDialogActions = friendsSelectionDialogActions
        )
    }
}

@Composable
fun SplitUserListLayout(
    modifier: Modifier = Modifier,
    transactionAmount: Double,
    currency: Currency,
    isSplitEvenly: Boolean,
    split: Map<User, String>,
    changeLoanForUser: (User, String) -> Unit
) {
    Column(modifier = modifier) {
        UserListItemSmall(
            user = User(name = stringResource(R.string.me)),
            trailingContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.widthIn(min = 80.dp, max = 120.dp),
                        value = (transactionAmount - split.values.sumOf {
                            it.toDoubleOrNull() ?: 0.0
                        })
                            .toStringWithTwoDecimalsAndNoTrailingZeros(),
                        onValueChange = {},
                        enabled = false,
                        textStyle = MaterialTheme.typography.titleMedium,
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    Text(
                        text = currency.sign, style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        )
        split.forEach { (friend, loan) ->
            UserListItemSmall(
                user = friend,
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            // Avoid fractional fillMaxWidth when row width is unknown
                            modifier = Modifier.widthIn(min = 80.dp, max = 120.dp),
                            value = loan,
                            onValueChange = { newValue ->
                                changeLoanForUser(friend, newValue)
                            },
                            enabled = !isSplitEvenly,
                            textStyle = MaterialTheme.typography.titleMedium,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Decimal
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                            )
                        )
                        Text(
                            text = currency.sign, style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun SplitLayoutPreview() {
    BudgetManagerTheme {
        val (transaction, updateTransaction) = rememberTransactionState()
        val (splitState, splitActions) = rememberSplitState(transaction.amount)

        SplitLayout(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            transaction = transaction,
            changeLoanForUser = splitActions.updateUserSplit,
            removeSplit = splitActions.removeSplit,
            split = splitState.splitData,
            isSplitEvenly = splitState.isSplitEvenly,
            friendsSelectionDialogState = splitState.friendsSelectionDialogState,
            friendsSelectionDialogActions = splitActions.friendsSelectionDialogActions,
            setSplitEvenly = splitActions.setSplitEvenly
        )
    }
}

