package com.wf.bm.feature.settlements.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettlementDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettlementDetailsViewModel = koinViewModel(),
    settlement: Settlement,
    goBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    if (state.settlement == null) {
        viewModel.setSettlement(settlement)
    }

    LaunchedEffect(state.shouldNavigateBack) {
        if (state.shouldNavigateBack) {
            goBack()
        }
    }

    SettlementDetailsScreen(
        modifier = modifier,
        user = state.user!!,
        settlement = settlement,
        goBack = goBack,
        deleteSettlement = { viewModel.deleteSettlement() }
    )
}
