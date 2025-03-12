package com.wf.bm

import android.app.Application
import com.wf.bm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.androix.startup.KoinStartup.onKoinStartup
import org.koin.core.logger.Level
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level.DEBUG

//@OptIn(KoinExperimentalAPI::class)
class BmApplication : Application() {
    init {
        onKoinStartup {
            androidContext(this@BmApplication)
            androidLogger(DEBUG)
            modules(appModule)
//            workManagerFactory()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}