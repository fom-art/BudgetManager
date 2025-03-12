package com.wf.bm.feature.goals.di

import com.wf.bm.feature.goals.create.CreateGoalViewModel
import com.wf.bm.feature.goals.main.GoalsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val goalsModule = module {
    viewModelOf(::CreateGoalViewModel)
    viewModelOf(::GoalsViewModel)
}