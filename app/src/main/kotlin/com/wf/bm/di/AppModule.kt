package com.wf.bm.di

import com.wf.bm.core.data.di.dataModule
import com.wf.bm.feature.authentication.di.authenticationModule
import com.wf.bm.feature.friends.di.friendsModule
import com.wf.bm.feature.goals.di.goalsModule
import com.wf.bm.feature.homepage.di.homepageModule
import com.wf.bm.feature.settings.di.settingsModule
import com.wf.bm.feature.settlements.di.settlementsModule
import com.wf.bm.feature.transactions.di.transactionsModule
import org.koin.dsl.module

val featureModules = module {
    includes(
        authenticationModule,
        friendsModule,
        goalsModule,
        homepageModule,
        settingsModule,
        settlementsModule,
        transactionsModule
    )
}

val appModule = module {
    includes(dataModule, featureModules)
}