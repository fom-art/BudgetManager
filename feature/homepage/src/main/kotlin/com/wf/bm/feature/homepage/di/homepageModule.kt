package com.wf.bm.feature.homepage.di

import com.wf.bm.feature.homepage.HomepageViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homepageModule = module {
    viewModelOf(::HomepageViewModel)
}