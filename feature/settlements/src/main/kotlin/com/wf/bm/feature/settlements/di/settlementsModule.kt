package com.wf.bm.feature.settlements.di

import com.wf.bm.feature.settlements.create.CreateSettlementViewModel
import com.wf.bm.feature.settlements.create.friend_selection.FriendSelectionDialogViewModel
import com.wf.bm.feature.settlements.details.SettlementDetailsViewModel
import com.wf.bm.feature.settlements.history.SettlementsHistoryViewModel
import com.wf.bm.feature.settlements.main.SettlementsViewModel
import com.wf.bm.feature.settlements.main.filter.FilterDialogViewModel
import com.wf.bm.feature.settlements.notifications.SettlementsNotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settlementsModule = module {
    viewModelOf(::CreateSettlementViewModel)
    viewModelOf(::FriendSelectionDialogViewModel)
    viewModelOf(::FilterDialogViewModel)
    viewModelOf(::SettlementDetailsViewModel)
    viewModelOf(::SettlementsViewModel)
    viewModelOf(::SettlementsHistoryViewModel)
    viewModelOf(::SettlementsNotificationsViewModel)
}