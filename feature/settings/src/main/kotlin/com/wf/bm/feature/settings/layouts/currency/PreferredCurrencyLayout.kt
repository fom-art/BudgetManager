package com.wf.bm.feature.settings.layouts.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.wf.bm.core.model.Currency
import com.wf.bm.feature.settings.layouts.SettingItem

@Composable
fun PreferredCurrencyLayout(
    preferredCurrency: Currency,
    onCurrencySelected: (Currency) -> Unit
) {
    var isCurrencyDialogVisible by remember { mutableStateOf(false) }

    SettingItem(
        title = "Preferred Currency",
        value = stringResource(preferredCurrency.currencyNameRes),
        onClick = { isCurrencyDialogVisible = true }
    )

    if (isCurrencyDialogVisible) {
        PreferredCurrencyDialog(
            currentCurrency = preferredCurrency,
            onCurrencySelected = {
                onCurrencySelected(it)
                isCurrencyDialogVisible = false
            },
            onDismiss = { isCurrencyDialogVisible = false }
        )
    }
}