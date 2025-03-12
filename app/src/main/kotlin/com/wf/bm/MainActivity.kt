package com.wf.bm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.data.repository.SettingsRepositoryImpl
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.di.appModule
import com.wf.bm.ui.BmApp
import com.wf.bm.ui.rememberBmAppState
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {

    private val authenticationManager: AuthenticationManager by inject()
    private val settingsRepository: SettingsRepositoryImpl by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberBmAppState(
                authenticationManager = authenticationManager,
                settingsRepository = settingsRepository,
            )

            CompositionLocalProvider {
                BudgetManagerTheme {
                    BmApp(appState = appState)
                }
            }
        }
    }
}