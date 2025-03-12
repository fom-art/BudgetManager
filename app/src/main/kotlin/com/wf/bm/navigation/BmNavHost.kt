package com.wf.bm.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.wf.bm.navigation.graphs.AuthenticationGraph
import com.wf.bm.navigation.graphs.HomepageGraph
import com.wf.bm.navigation.graphs.authenticationGraph
import com.wf.bm.navigation.graphs.friendsGraph
import com.wf.bm.navigation.graphs.goalsGraph
import com.wf.bm.navigation.graphs.homepageGraph
import com.wf.bm.navigation.graphs.settingsGraph
import com.wf.bm.navigation.graphs.settlementsGraph
import com.wf.bm.navigation.graphs.transactionsGraph
import com.wf.bm.ui.BmAppState
import kotlin.math.sign

@Composable
fun BmNavHost(
    modifier: Modifier = Modifier,
    appState: BmAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    isLoggedIn: Boolean,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) HomepageGraph else AuthenticationGraph,
        modifier = modifier,
    ) {
        authenticationGraph(navController = navController, onShowSnackbar = onShowSnackbar)
        friendsGraph(navController = navController)
        goalsGraph(navController = navController)
        homepageGraph(navController = navController)
        settingsGraph(navController = navController)
        settlementsGraph(navController = navController)
        transactionsGraph(navController = navController)
    }
}