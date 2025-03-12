package com.wf.bm.feature.transactions.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton
import com.wf.bm.core.designsystem.values.paddingLarge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import com.wf.bm.feature.transactions.create.category.CategoriesLayout
import com.wf.bm.core.designsystem.layouts.expenses.ExpensesLayout
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogActions
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogState
import com.wf.bm.feature.transactions.create.split.SplitLayout
import com.wf.bm.core.designsystem.layouts.time.DateTimeLayout
import com.wf.bm.feature.transactions.common.preview.rememberSplitState
import com.wf.bm.feature.transactions.common.preview.rememberTransactionState
import com.wf.bm.feature.transactions.create.repeatability.RepeatabilitySetUpLayout

@Composable
internal fun CreateTransactionBottomSheetDialog(
    modifier: Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit,
    updateUserSplit: (User, String) -> Unit,
    split: Map<User, String>?,
    removeSplit: () -> Unit,
    isSplitEvenly: Boolean,
    setSplitEvenly: (Boolean) -> Unit,
    friendsSelectionDialogState: FriendsSelectionDialogState,
    friendsSelectionDialogActions: FriendsSelectionDialogActions,
    dismiss: () -> Unit,
    submit: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f))
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable { dismiss() }
        )
        CreateTransactionLayout(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                )
                .background(MaterialTheme.colorScheme.surfaceContainer),
            transaction = transaction,
            updateTransaction = updateTransaction,
            submit = submit,
            updateUserSplit = updateUserSplit,
            split = split,
            removeSplit = removeSplit,
            isSplitEvenly = isSplitEvenly,
            setSplitEvenly = setSplitEvenly,
            friendsSelectionDialogState = friendsSelectionDialogState,
            friendsSelectionDialogActions = friendsSelectionDialogActions
        )
    }
}

@Composable
fun CreateTransactionLayout(
    modifier: Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit,
    updateUserSplit: (User, String) -> Unit,
    split: Map<User, String>?,
    removeSplit: () -> Unit,
    isSplitEvenly: Boolean,
    setSplitEvenly: (Boolean) -> Unit,
    friendsSelectionDialogState: FriendsSelectionDialogState,
    friendsSelectionDialogActions: FriendsSelectionDialogActions,
    submit: () -> Unit,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = paddingMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(paddingLarge))
            TransactionNameTextField(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingLarge))
            ExpensesLayout(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingLarge))
            Text(
                text = stringResource(R.string.time),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            DateTimeLayout(
                modifier = Modifier.padding(16.dp),
                date = transaction.date,
                updateDate = { newDate -> updateTransaction(transaction.copy(date = newDate)) }
            )
            Spacer(Modifier.height(paddingMedium))
            CategoriesLayout(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            Spacer(Modifier.height(paddingMedium))
            RepeatabilitySetUpLayout(
                transaction = transaction,
                updateTransaction = updateTransaction
            )
            if (!transaction.isPositive) {
                Spacer(Modifier.height(paddingLarge))
            }
            AnimatedVisibility(visible = !transaction.isPositive) {
                SplitLayout(
                    transaction = transaction,
                    split = split,
                    removeSplit = removeSplit,
                    isSplitEvenly = isSplitEvenly,
                    friendsSelectionDialogState = friendsSelectionDialogState,
                    friendsSelectionDialogActions = friendsSelectionDialogActions,
                    changeLoanForUser = updateUserSplit,
                    setSplitEvenly = setSplitEvenly
                )
            }
            Spacer(Modifier.height(paddingLarge))
            BmFilledButton(
                text = stringResource(com.wf.bm.core.designsystem.R.string.submit),
                onClick = submit
            )
            Spacer(Modifier.height(paddingLarge))
        }
    }
}

@Composable
fun TransactionNameTextField(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxSize(),
        value = transaction.title,
        onValueChange = { newTitle ->
            updateTransaction(transaction.copy(title = newTitle))
        },
        textStyle = MaterialTheme.typography.headlineLarge,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
        )
    )
}


@Preview(showBackground = true)
@Composable
fun CreateTransactionBottomSheetDialogPreview() {
    BudgetManagerTheme {
        // Transaction state
        val (transaction, updateTransaction) = rememberTransactionState()

        // Split state and actions
        val (splitState, splitActions) = rememberSplitState(transaction.amount)

        // Main layout
        BudgetManagerTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CreateTransactionBottomSheetDialog(
                    modifier = Modifier,
                    transaction = transaction,
                    updateTransaction = updateTransaction,
                    submit = {
                        println("Submit transaction clicked")
                    },
                    updateUserSplit = splitActions.updateUserSplit,
                    split = splitState.splitData,
                    removeSplit = splitActions.removeSplit,
                    isSplitEvenly = splitState.isSplitEvenly,
                    setSplitEvenly = splitActions.setSplitEvenly,
                    dismiss = {
                        println("Dialog dismissed")
                    },
                    friendsSelectionDialogState = splitState.friendsSelectionDialogState,
                    friendsSelectionDialogActions = splitActions.friendsSelectionDialogActions
                )
            }
        }
    }
}
