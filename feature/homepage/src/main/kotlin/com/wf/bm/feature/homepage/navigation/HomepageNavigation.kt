package com.wf.bm.feature.homepage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.feature.homepage.HomepageRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomepageScreen

fun NavController.navigateToHomepage(navOptions: NavOptions? = null) = navigate(HomepageScreen, navOptions)

fun NavGraphBuilder.homepageScreen() {
    composable<HomepageScreen> {
        HomepageRoute()
    }
}