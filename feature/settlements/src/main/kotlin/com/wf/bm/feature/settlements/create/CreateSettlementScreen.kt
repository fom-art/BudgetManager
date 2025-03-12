package com.wf.bm.feature.settlements.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.components.UserListItem
import com.wf.bm.core.designsystem.components.buttons.ToggleButton
import com.wf.bm.core.designsystem.layouts.expenses.ExpensesLayout
import com.wf.bm.core.designsystem.layouts.time.DateTimeLayout
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.User
import com.wf.bm.feature.settlements.R
import com.wf.bm.feature.settlements.create.friend_selection.FriendSelectionDialog
import com.wf.bm.feature.settlements.create.friend_selection.FriendSelectionDialogActions
import com.wf.bm.feature.settlements.create.friend_selection.FriendSelectionDialogState
import java.time.LocalDateTime

@Composable
fun CreateSettlementScreen(
    modifier: Modifier = Modifier,
    createSettlementState: CreateSettlementState,
    createSettlementActions: CreateSettlementActions,
    friendSelectionDialogState: FriendSelectionDialogState,
    friendSelectionDialogActions: FriendSelectionDialogActions,
    goBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(goBack = goBack)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedGradientBackground()
            CreateSettlementsLayout(
                modifier = Modifier
                    .padding(innerPadding)
                    .align(Alignment.Center),
                screenState = createSettlementState,
                screenActions = createSettlementActions
            )
        }
    }

    if (friendSelectionDialogState.isVisible) {
        FriendSelectionDialog(
            title = stringResource(R.string.select_friend),
            friendSelectionDialogState = friendSelectionDialogState,
            friendSelectionDialogActions = friendSelectionDialogActions
        )
    }
}

@Composable
fun CreateSettlementsLayout(
    modifier: Modifier = Modifier,
    screenState: CreateSettlementState,
    screenActions: CreateSettlementActions,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Toggle Loan or Debt
            val options = listOf(
                stringResource(R.string.loan),
                stringResource(R.string.debt),
            )
            ToggleButton(
                options = options,
                selectedOptionIndex = if (screenState.isLoan) 0 else 1,
                onOptionSelected = { index ->
                    if (index == 0) screenActions.setIsLoan(true) else screenActions.setIsLoan(
                        false
                    )
                }
            )

            // Amount and Currency
            ExpensesLayout(
                modifier = Modifier.padding(horizontal = 16.dp),
                amount = screenState.amount,
                currency = screenState.currency,
                updateAmount = screenActions.setAmount,
                updateCurrency = screenActions.setCurrency
            )

            // Date and Time
            DateTimeLayout(
                date = screenState.dateTime,
                updateDate = screenActions.setDateTime
            )

            // Friend Selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = screenActions.showFriendSelectionDialog,
                ) {
                    Text(
                        text = "Search Friend",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (screenState.selectedFriend != null) {
                UserListItem(
                    user = screenState.selectedFriend,
                    containerColor = Color.Transparent,
                    trailingContent = {
                        IconButton(onClick = screenActions.removeSelectedFriend) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = stringResource(R.string.remove_selected_friend)
                            )
                        }
                    }
                )
            }

            // Submit Button
            Button(
                onClick = screenActions.submit,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(com.wf.bm.core.designsystem.R.string.done),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateSettlementScreenPreview() {
    val mockUser = User(
        id = 1,
        name = "John",
        surname = "Doe",
        nickname = "johndoe"
    )

    // Mutable state for the preview
    var isLoan by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf(400.0) }
    var currency by remember { mutableStateOf(Currency.USD) }
    var dateTime by remember { mutableStateOf(LocalDateTime.now()) }

    // State and actions for the friend selection dialog
    var isFriendDialogVisible by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val allFriends = listOf(
        User(1, "John Doe", nickname = "johndoe"),
        User(2, "Jane Doe", nickname = "janedoe"),
        User(3, "Alice Smith", nickname = "alice"),
        User(4, "Bob Brown", nickname = "bobbrown"),
        User(5, "Charlie Johnson", nickname = "charlie")
    )
    var chosenFriend by remember { mutableStateOf<User?>(null) }
    val filteredFriends = allFriends.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.nickname.contains(
            searchQuery,
            ignoreCase = true
        )
    }

    val friendSelectionDialogState = FriendSelectionDialogState(
        friends = filteredFriends,
        searchQuery = searchQuery,
        isVisible = isFriendDialogVisible
    )

    val friendSelectionDialogActions = FriendSelectionDialogActions(
        setSearchQuery = { searchQuery = it },
        onDismissRequest = { isFriendDialogVisible = false },
        selectFriend = { friend ->
            chosenFriend = friend
            isFriendDialogVisible = false
        },
        selectCustomFriend = { friend ->
            chosenFriend = friend
            isFriendDialogVisible = false
        },
        setDialogVisibility = { isFriendDialogVisible = it }
    )

    val createSettlementState = CreateSettlementState(
        isLoan = isLoan,
        amount = amount,
        currency = currency,
        dateTime = dateTime,
        selectedFriend = chosenFriend
    )

    val createSettlementActions = CreateSettlementActions(
        setIsLoan = { newIsLoan -> isLoan = newIsLoan },
        setAmount = { newAmount -> amount = newAmount },
        setCurrency = { newCurrency -> currency = newCurrency },
        setDateTime = { newDateTime -> dateTime = newDateTime },
        setSelectedFriend = { newFriend -> chosenFriend = newFriend },
        removeSelectedFriend = { chosenFriend = null },
        showFriendSelectionDialog = { isFriendDialogVisible = true },
        submit = {}
    )

    BudgetManagerTheme {
        CreateSettlementScreen(
            createSettlementState = createSettlementState,
            createSettlementActions = createSettlementActions,
            friendSelectionDialogState = friendSelectionDialogState,
            friendSelectionDialogActions = friendSelectionDialogActions,
            goBack = { println("Go back clicked") }
        )
    }
}