package com.wf.bm.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import com.wf.bm.core.designsystem.components.BmNavigationSuiteScaffold
import com.wf.bm.navigation.BmNavHost
import com.wf.bm.navigation.graphs.HomepageGraph
import kotlin.reflect.KClass

@Composable
fun BmApp(
    modifier: Modifier = Modifier,
    appState: BmAppState,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val currentDestination = appState.currentTopLevelDestination
    val snackbarHostState = remember { SnackbarHostState() }
appState.isDarkTheme

    BmNavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val selected = currentDestination == destination
                item(
                    selected = selected,
                    onClick = {
                        Log.d("navigationSuiteItems", "navigation with destination: $destination")
                        appState.navigateToTopLevelDestination(destination)
                    },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.iconTextId),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    modifier =
                    Modifier
                        .testTag("BmNavItem")
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
        showNavigationBar = appState.showNavigationBar,
    ) {
        BmBackground()
        BmNavHost(
            appState = appState,
            onShowSnackbar = { message, action ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = Short,
                ) == ActionPerformed
            },
            isLoggedIn = appState.user != null,
        )
    }

    LaunchedEffect(Unit) { appState.observeUserChanges() }
}

@Composable
fun BmBackground() {
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background))
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false