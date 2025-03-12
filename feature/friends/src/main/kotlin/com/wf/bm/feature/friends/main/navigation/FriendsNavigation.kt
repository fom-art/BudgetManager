package com.wf.bm.feature.friends.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wf.bm.core.model.User
import com.wf.bm.feature.friends.main.FriendsRoute
import kotlinx.serialization.Serializable

@Serializable
data object FriendsScreen

fun NavController.navigateToFriends(navOptions: NavOptions? = null) =
    navigate(FriendsScreen, navOptions)

fun NavGraphBuilder.friendsScreen(
    openFriendDetails: (User) -> Unit,
) {
    composable<FriendsScreen> {
        FriendsRoute(openFriendDetails = openFriendDetails)
    }
}