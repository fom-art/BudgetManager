package com.wf.bm.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.User
import com.wf.bm.feature.authentication.sign_in.navigation.navigateToSignIn
import kotlinx.coroutines.CoroutineScope
import androidx.tracing.trace
import com.wf.bm.core.data.repository.SettingsRepositoryImpl
import com.wf.bm.navigation.graphs.AuthenticationGraph
import com.wf.bm.navigation.graphs.HomepageGraph
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

@Composable
fun rememberBmAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    authenticationManager: AuthenticationManager,
    settingsRepository: SettingsRepositoryImpl,
): BmAppState {
    return remember(
        navController,
        coroutineScope
    ) {
        BmAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            authenticationManager = authenticationManager,
            settingsRepository = settingsRepository
        )
    }
}

@Stable
class BmAppState(
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    private val authenticationManager: AuthenticationManager,
    private val settingsRepository: SettingsRepositoryImpl,
) {
    val user: User? = authenticationManager.user
    val isLoggedIn = authenticationManager.isLoggedIn
    val isDarkTheme = coroutineScope.launch { settingsRepository.getIsDarkTheme() }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            val current = currentDestination
            val topLevel = TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                current?.hasRoute(route = topLevelDestination.screenRoute) ?: false
            }
            Log.d("NavigationDebug", "Current: $current, TopLevel: $topLevel")
            return topLevel
        }
    val showNavigationBar
        @Composable get() = currentTopLevelDestination != null

    init {
        observeUserChanges()
    }

    private fun logOut() {
        navController.navigate(AuthenticationGraph) {
            popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
        }
        Log.d("App State", "Log Out :]")
    }

    private fun logIn() {
        navController.navigate(HomepageGraph) {
            popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
        }
        Log.d("App State", "Log In :]")
    }

    @OptIn(InternalSerializationApi::class)
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            val route = topLevelDestination.graphRoute.objectInstance
                ?: throw IllegalArgumentException("Route ${topLevelDestination.graphRoute.simpleName} must be a data object")

            navController.navigate(route, topLevelNavOptions)
        }
    }

    fun observeUserChanges() {
        coroutineScope.launch {
            isLoggedIn.collectLatest { isLoggedIn ->
                // Ensure actions are executed based on the user's state
                handleUserStateChange(isLoggedIn)
            }
        }
    }

    private fun handleUserStateChange(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            logIn()
        } else {
            logOut()
        }
    }
}