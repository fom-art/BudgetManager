package com.wf.bm.feature.settlements.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettlementsHistoryRoute(
    modifier: Modifier = Modifier,
    viewModel: SettlementsHistoryViewModel = koinViewModel(),
    goToSettlementDetails: (NavOptions?, Settlement) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SettlementsHistoryScreen(
        modifier = modifier,
        user = state.user ?: User(id = 0, name = "", surname = "", nickname = ""),
        settlements = state.settlements,
        deleteSettlement = { settlement -> viewModel.deleteSettlement(settlement) },
        goToSettlementDetails = { settlement -> goToSettlementDetails(null, settlement) },
    )
}
