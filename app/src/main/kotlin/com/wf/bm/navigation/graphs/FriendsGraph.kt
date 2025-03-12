package com.wf.bm.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.wf.bm.core.model.User
import com.wf.bm.feature.friends.details.navigation.friendDetailsScreen
import com.wf.bm.feature.friends.details.navigation.navigateToFriendDetails
import com.wf.bm.feature.friends.main.navigation.FriendsScreen
import com.wf.bm.feature.friends.main.navigation.friendsScreen
import kotlinx.serialization.Serializable

@Serializable
data object FriendsGraph

fun NavGraphBuilder.friendsGraph(
    navController: NavController,
) {
    navigation<FriendsGraph>(
        startDestination = FriendsScreen,
    ) {
        friendsScreen(
            openFriendDetails = navController::navigateToFriendDetails
        )
        friendDetailsScreen(
            goBack = navController::popBackStack
        )
    }
}