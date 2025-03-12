package com.wf.bm.feature.settlements.notifications

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import com.wf.bm.feature.settlements.R
import java.time.LocalDateTime

@Composable
fun SettlementsNotificationsScreen(
    modifier: Modifier = Modifier,
    user: User,
    settlements: List<Settlement>,
    submitNotification: (Settlement) -> Unit,
    rejectNotification: (Settlement) -> Unit,
    goBack: () -> Unit
) {
    Scaffold(modifier = modifier,
        topBar = {
            DefaultTopBar(
                goBack = goBack,
                title = stringResource(R.string.notifications)
            )
        }) { innerPadding ->
        SettlementNotificationsList(
            modifier = Modifier.padding(innerPadding),
            user = user,
            settlements = settlements,
            submitNotification = submitNotification,
            rejectNotification = rejectNotification
        )
    }
}

@Composable
fun SettlementNotificationsList(
    modifier: Modifier = Modifier,
    user: User,
    settlements: List<Settlement>,
    submitNotification: (Settlement) -> Unit,
    rejectNotification: (Settlement) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(settlements) { settlement ->
            SettlementNotificationsListItem(
                user = user,
                settlement = settlement,
                submitNotification = submitNotification,
                rejectNotification = rejectNotification
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettlementsNotificationsScreenPreview() {
    // Mock user
    val user = User(
        id = 1,
        name = "John",
        surname = "Doe",
        nickname = "johndoe",
        photo = null
    )

    // Mock friends
    val friend1 = User(
        id = 2,
        name = "Jane",
        surname = "Smith",
        nickname = "janesmith",
        photo = null
    )

    val friend2 = User(
        id = 3,
        name = "Paul",
        surname = "Brown",
        nickname = "paulbrown",
        photo = null
    )

    val friend3 = User(
        id = 4,
        name = "Alice",
        surname = "Johnson",
        nickname = "alicejohnson",
        photo = null
    )

    // Mock settlements with various statuses
    val mockSettlements = listOf(
        Settlement(
            debtor = user,
            creditor = friend1,
            status = SettlementStatus.SEND,
            amount = 200.0,
            currency = Currency.USD,
            creationDateTime = LocalDateTime.now().minusDays(2)
        ),
        Settlement(
            debtor = friend2,
            creditor = user,
            status = SettlementStatus.SEND,
            amount = 150.0,
            currency = Currency.EUR,
            creationDateTime = LocalDateTime.now().minusWeeks(1)
        ),
        Settlement(
            debtor = user,
            creditor = friend3,
            status = SettlementStatus.SEND,
            amount = 300.0,
            currency = Currency.UAH,
            creationDateTime = LocalDateTime.now().minusMonths(1)
        ),
        Settlement(
            debtor = friend3,
            creditor = user,
            status = SettlementStatus.SEND,
            amount = 50.0,
            currency = Currency.USD,
            creationDateTime = LocalDateTime.now().minusDays(5)
        ),
        Settlement(
            debtor = user,
            creditor = friend2,
            status = SettlementStatus.SEND,
            amount = 75.0,
            currency = Currency.UAH,
            creationDateTime = LocalDateTime.now().minusHours(12)
        )
    )

    BudgetManagerTheme {
        SettlementsNotificationsScreen(
            modifier = Modifier,
            user = user,
            settlements = mockSettlements,
            submitNotification = { /* No-op for preview */ },
            rejectNotification = { /* No-op for preview */ },
            goBack = { /* No-op for preview */ }
        )
    }
}
