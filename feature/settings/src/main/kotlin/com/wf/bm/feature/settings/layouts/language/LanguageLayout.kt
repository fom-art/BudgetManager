package com.wf.bm.feature.settings.layouts.language

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.wf.bm.feature.settings.R
import com.wf.bm.feature.settings.layouts.SettingItem

@Composable
fun LanguageLayout(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    var isLanguageDialogVisible by remember { mutableStateOf(false) }

    Column {
        SettingItem(
            title = stringResource(R.string.language),
            value = selectedLanguage,
            onClick = { isLanguageDialogVisible = true }
        )

        if (isLanguageDialogVisible) {
            LanguageSelectionDialog(
                selectedLanguage = selectedLanguage,
                onLanguageSelected = {
                    onLanguageSelected(it)
                    isLanguageDialogVisible = false
                },
                onDismiss = { isLanguageDialogVisible = false }
            )
        }
    }
}
