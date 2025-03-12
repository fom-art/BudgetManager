package com.wf.bm.feature.settlements.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import com.wf.bm.feature.settlements.main.filter.FilterDialog
import com.wf.bm.feature.settlements.main.filter.FilterDialogActions
import com.wf.bm.feature.settlements.main.filter.FilterDialogViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettlementsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettlementsViewModel = koinViewModel(),
    filterDialogViewModel: FilterDialogViewModel = koinViewModel(),
    goToNotifications: () -> Unit,
    goToSettlementDetails: (NavOptions?, Settlement) -> Unit,
    goToSettlementsHistory: () -> Unit,
    goToCreateSettlement: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val filterDialogState = filterDialogViewModel.state.collectAsStateWithLifecycle().value

    SettlementsScreen(
        modifier = modifier,
        user = state.user ?: User(id = 0, name = "", surname = "", nickname = ""),
        notificationsCount = state.notificationsCount,
        settlements = state.settlements,
        deleteSettlement = { settlement -> viewModel.deleteSettlement(settlement) },
        goToNotifications = goToNotifications,
        goToSettlementDetails = { settlement -> goToSettlementDetails(null, settlement) },
        goToSettlementsHistory = goToSettlementsHistory,
        goToCreateSettlement = goToCreateSettlement,
        openFilterSettlementsDialog = { filterDialogViewModel.setDialogVisibility(true) }
    )

    if (filterDialogState.isVisible) {
        FilterDialog(
            filterDialogState = filterDialogState,
            filterDialogActions = FilterDialogActions(
                setSearchQuery = filterDialogViewModel::setSearchQuery,
                onDismissRequest = { filterDialogViewModel.setDialogVisibility(false) },
                addFriend = filterDialogViewModel::addFriend,
                removeFriend = filterDialogViewModel::removeFriend,
                submit = {
                    filterDialogViewModel.submit()
                    filterDialogViewModel.setDialogVisibility(false)
                },
                setDialogVisibility = filterDialogViewModel::setDialogVisibility,
                setDebtChecked = filterDialogViewModel::setDebtChecked,
                setLoanChecked = filterDialogViewModel::setLoanChecked,
            )
        )
    }
}
