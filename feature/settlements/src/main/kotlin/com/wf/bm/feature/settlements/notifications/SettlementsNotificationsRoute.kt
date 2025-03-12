package com.wf.bm.feature.settlements.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wf.bm.core.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettlementsNotificationsRoute(
    modifier: Modifier = Modifier,
    viewModel: SettlementsNotificationsViewModel = koinViewModel(),
    goBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    SettlementsNotificationsScreen(
        modifier = modifier,
        user = state.user ?: User(),
        settlements = state.settlements,
        submitNotification = { settlement -> viewModel.submitNotification(settlement) },
        rejectNotification = { settlement -> viewModel.rejectNotification(settlement) },
        goBack = goBack
    )
}
