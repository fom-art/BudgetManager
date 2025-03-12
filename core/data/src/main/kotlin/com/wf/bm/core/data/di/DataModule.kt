package com.wf.bm.core.data.di

import com.wf.bm.core.data.database.GoalsDatabase
import com.wf.bm.core.data.database.SettlementsDatabase
import com.wf.bm.core.data.database.TransactionsDatabase
import com.wf.bm.core.data.database.UsersDatabase
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.data.util.AuthenticationManagerImpl
import com.wf.bm.core.data.repository.UserRepository
import com.wf.bm.core.data.repository.UserRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.wf.bm.core.data.repository.*


val dataModule = module {
    singleOf(::AuthenticationManagerImpl) bind AuthenticationManager::class

    singleOf(::UserRepositoryImpl) bind UserRepository::class

    singleOf(::GoalsRepositoryImpl) bind GoalsRepository::class

    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class

    singleOf(::SettlementsRepositoryImpl) bind SettlementsRepository::class

    singleOf(::TransactionsRepositoryImpl) bind TransactionsRepository::class

    single { GoalsDatabase() }
    single { TransactionsDatabase() }
    single { UsersDatabase(get(), get()) }
    single { SettlementsDatabase(get()) }

}
