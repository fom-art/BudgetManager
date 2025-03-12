package com.wf.bm.feature.friends.details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wf.bm.core.model.User
import com.wf.bm.core.utils.CustomNavType
import com.wf.bm.feature.friends.details.FriendDetailsRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class FriendDetailsScreen(
    val friend: User
)

fun NavController.navigateToFriendDetails(friend: User, navOptions: NavOptions? = null) =
    navigate(FriendDetailsScreen(friend = friend), navOptions)

fun NavGraphBuilder.friendDetailsScreen(
    goBack: () -> Unit
) {
    composable<FriendDetailsScreen>(
        typeMap = mapOf(typeOf<User>() to CustomNavType(User::class.java, User.serializer()))
    ) {
        val args = it.toRoute<FriendDetailsScreen>()
        FriendDetailsRoute(friend = args.friend, goBack = goBack)
    }
}