package com.wf.bm.feature.settlements.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.wf.bm.feature.settlements.main.SettlementsList
import java.time.LocalDateTime

@Composable
fun SettlementsHistoryScreen(
    modifier: Modifier,
    user: User,
    settlements: List<Settlement>,
    deleteSettlement: (Settlement) -> Unit,
    goToSettlementDetails: (Settlement) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = stringResource(R.string.history),
            )
        },
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
        SettlementsHistoryScreen(
            modifier = Modifier,
            user = user,
            settlements = mockSettlements,
            deleteSettlement = { settlement -> mockSettlements.remove(settlement) },
            goToSettlementDetails = { },
        )
    }
}