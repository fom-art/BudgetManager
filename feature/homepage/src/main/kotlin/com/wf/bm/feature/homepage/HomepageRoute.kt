package com.wf.bm.feature.homepage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomepageRoute(
    modifier: Modifier = Modifier,
    viewModel: HomepageViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    state.user?.let {
        HomepageScreen(
            modifier = modifier,
            user = it,
            preferredCurrency = state.preferredCurrency,
            expenses = state.expenses,
            goals = state.goals,
        )
    }
}