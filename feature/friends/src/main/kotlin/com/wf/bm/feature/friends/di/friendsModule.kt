package com.wf.bm.feature.friends.di

import com.wf.bm.feature.friends.details.FriendDetailsViewModel
import com.wf.bm.feature.friends.main.FriendsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val friendsModule = module {
    viewModelOf(::FriendDetailsViewModel)
    viewModelOf(::FriendsViewModel)
}