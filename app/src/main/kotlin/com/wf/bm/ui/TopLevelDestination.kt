package com.wf.bm.ui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.wf.bm.feature.friends.main.navigation.FriendsScreen
import com.wf.bm.feature.homepage.navigation.HomepageScreen
import com.wf.bm.feature.settings.navigation.SettingsScreen
import com.wf.bm.feature.settlements.main.navigation.SettlementsScreen
import com.wf.bm.feature.transactions.main.navigation.TransactionsScreen
import com.wf.bm.icons.BmIcons
import com.wf.bm.navigation.graphs.FriendsGraph
import com.wf.bm.navigation.graphs.HomepageGraph
import com.wf.bm.navigation.graphs.SettingsGraph
import com.wf.bm.navigation.graphs.SettlementsGraph
import com.wf.bm.navigation.graphs.TransactionsGraph
import kotlin.reflect.KClass
import com.wf.bm.core.designsystem.R as designSystemR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    val graphRoute: KClass<*>,
    val screenRoute: KClass<*>,
) {
    FRIENDS(
        selectedIcon = BmIcons.People,
        unselectedIcon = BmIcons.PeopleBorder,
        iconTextId = designSystemR.string.friends,
        graphRoute = FriendsGraph::class,
        screenRoute = FriendsScreen::class,
    ),
    SETTLEMENTS(
        selectedIcon = BmIcons.Money,
        unselectedIcon = BmIcons.MoneyBorder,
        iconTextId = designSystemR.string.loans,
        graphRoute = SettlementsGraph::class,
        screenRoute = SettlementsScreen::class,
    ),
    TRANSACTIONS(
        selectedIcon = BmIcons.Summary,
        unselectedIcon = BmIcons.SummaryBorder,
        iconTextId = designSystemR.string.transactions,
        graphRoute = TransactionsGraph::class,
        screenRoute = TransactionsScreen::class,
    ),
    ANALYTICS(
        selectedIcon = BmIcons.Analytics,
        unselectedIcon = BmIcons.AnalyticsBorder,
        iconTextId = designSystemR.string.analytics,
        graphRoute = HomepageGraph::class,
        screenRoute = HomepageScreen::class,
    ),
    SETTINGS(
        selectedIcon = BmIcons.Settings,
        unselectedIcon = BmIcons.SettingsBorder,
        iconTextId = designSystemR.string.settings,
        graphRoute = SettingsGraph::class,
        screenRoute = SettingsScreen::class,
    ),
}
