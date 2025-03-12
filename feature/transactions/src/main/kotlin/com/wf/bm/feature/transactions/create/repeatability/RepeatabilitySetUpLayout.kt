package com.wf.bm.feature.transactions.create.repeatability

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.RepetitionPeriods
import com.wf.bm.core.model.Transaction
import com.wf.bm.feature.transactions.R
import com.wf.bm.core.designsystem.layouts.DropdownMenuLayout

@Composable
fun RepeatabilitySetUpLayout(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit
) {
    // Checkbox state
    var isChecked by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.repeatable_transaction),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(paddingMedium))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Checkbox
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    if (!isChecked) {
                        updateTransaction(transaction.copy(repeatableTransaction = null))
                    }
                }
            )

            // "Every" text
            Text(
                text = stringResource(R.string.every),
                style = MaterialTheme.typography.headlineSmall,
                color = if (isChecked) MaterialTheme.colorScheme.onBackground
                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.width(paddingMedium))

            // Dropdown Menu
            DropdownMenuLayout(
                selectedItem = transaction.repeatableTransaction?.let { stringResource(it.periodNameRes) }
                    ?: stringResource(R.string.select),
                items = RepetitionPeriods.entries.map { it.periodNameRes },
                selectAnItem = { periodNameRes ->
                    updateTransaction(
                        transaction.copy(
                            repeatableTransaction = RepetitionPeriods.findRepetitionPeriodFromRes(
                                periodNameRes
                            )!!
                        )
                    )
                },
                enabled = isChecked
            )
        }
    }
}