package com.wf.bm.core.designsystem.layouts.expenses

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wf.bm.core.designsystem.layouts.DropdownMenuLayout
import com.wf.bm.core.designsystem.layouts.TransactionPositivityIcon
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Transaction

@Composable
fun ExpensesLayout(
    modifier: Modifier = Modifier,
    amount: Double,
    currency: Currency,
    updateAmount: (Double) -> Unit,
    updateCurrency: (Currency) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        ExpensesTextField(
            modifier = Modifier.weight(1f),
            amount = amount,
            updateAmount = updateAmount,
        )
        Spacer(modifier = Modifier.width(paddingMedium))
        DropdownMenuLayout(
            selectedItem = currency.sign,
            items = Currency.entries.map { it.currencyNameRes },
            selectAnItem = { currencyNameRes ->
                updateCurrency(Currency.findCurrencyNameRes(currencyNameRes)!!)
            }
        )
    }
}

@Composable
fun ExpensesTextField(
    modifier: Modifier = Modifier,
    amount: Double,
    updateAmount: (Double) -> Unit,
) {
    var amountText by remember { mutableStateOf(amount.toString()) }

    LaunchedEffect(amount) {
        amountText = amount.toString()
    }

    TextField(
        value = amountText,
        onValueChange = { newText ->
            amountText = newText
            newText.toDoubleOrNull()?.let { newAmount ->
                updateAmount(newAmount)
            }
        },
        modifier = modifier,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Composable
fun ExpensesLayout(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TransactionPositivityIcon(
            modifier = Modifier.size(56.dp),
            isPositive = transaction.isPositive,
            changePositivity = { isPositive -> updateTransaction(transaction.copy(isPositive = isPositive)) }
        )
        Spacer(modifier = Modifier.width(paddingMedium))

        ExpensesLayout(
            modifier = Modifier.weight(1f),
            amount = transaction.amount,
            currency = transaction.currency,
            updateAmount = { newAmount -> updateTransaction(transaction.copy(amount = newAmount)) },
            updateCurrency = { newCurrency -> updateTransaction(transaction.copy(currency = newCurrency)) }
        )
    }
}
