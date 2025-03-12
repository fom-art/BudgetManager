package com.wf.bm.feature.settlements.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.components.NotificationIconWithBadge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import com.wf.bm.feature.settlements.R
import java.time.LocalDateTime

@Composable
fun SettlementsScreen(
    modifier: Modifier,
    user: User,
    notificationsCount: Int,
    settlements: List<Settlement>,
    deleteSettlement: (Settlement) -> Unit,
    goToNotifications: () -> Unit,
    goToSettlementDetails: (Settlement) -> Unit,
    goToSettlementsHistory: () -> Unit,
    goToCreateSettlement: () -> Unit,
    openFilterSettlementsDialog: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = stringResource(R.string.settlements),
                actions = {
                    Row {
                        NotificationIconWithBadge(
                            modifier = Modifier.clickable { goToNotifications() },
                            notificationCount = notificationsCount
                        )
                        Spacer(Modifier.width(paddingMedium))
                    }
                }
            )
        },
        floatingActionButton = {
            ExpandableFabMenu(
                goToSettlementsHistory = goToSettlementsHistory,
                goToCreateSettlement = goToCreateSettlement,
                openFilterSettlementsDialog = openFilterSettlementsDialog
            )
        }
    ) { innerPadding ->
        SettlementsList(
            modifier = Modifier.padding(innerPadding),
            user = user,
            settlements = settlements,
            deleteSettlement = deleteSettlement,
            goToSettlementDetails = goToSettlementDetails,
        )
    }
}

@Composable
fun SettlementsList(
    modifier: Modifier,
    user: User,
    settlements: List<Settlement>,
    deleteSettlement: (Settlement) -> Unit,
    goToSettlementDetails: (Settlement) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(settlements) { settlement ->
            TransactionDismissibleListItem(
                modifier = Modifier.clickable { goToSettlementDetails(settlement) },
                user = user,
                settlement = settlement,
                onRemove = deleteSettlement
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettlementsMainScreenPreview() {
    val user = User(
        id = 1,
        name = "John",
        surname = "Doe",
        nickname = "johndoe",
        photo = null
    )

    val friend = User(
        id = 2,
        name = "Jane",
        surname = "Smith",
        nickname = "janesmith",
        photo = null
    )

    val mockSettlements = remember {
        mutableStateListOf(
            Settlement(
                debtor = user,
                creditor = friend,
                status = SettlementStatus.PAID,

                amount = 150.0,
                currency = Currency.USD,
                creationDateTime = LocalDateTime.now().minusDays(2)
            ),
            Settlement(
                debtor = friend,
                creditor = user,
                status = SettlementStatus.PAID,
                amount = 200.0,
                currency = Currency.EUR,
                creationDateTime = LocalDateTime.now().minusWeeks(1)
            ),
            Settlement(
                debtor = user,
                creditor = friend,
                status = SettlementStatus.PAID,
                amount = 75.5,
                currency = Currency.UAH,
                creationDateTime = LocalDateTime.now().minusMonths(1)
            )
        )
    }

    BudgetManagerTheme {
        SettlementsScreen(
            modifier = Modifier,
            user = user,
            notificationsCount = 2,
            settlements = mockSettlements,
            deleteSettlement = { settlement -> mockSettlements.remove(settlement) },
            goToNotifications = { },
            goToSettlementDetails = { },
            goToSettlementsHistory = { },
            goToCreateSettlement = { },
            openFilterSettlementsDialog = { }
        )
    }
}