package com.wf.bm.feature.transactions.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.wf.bm.core.designsystem.components.SwipeLeftToDeleteBox
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.utils.toFullDescriptionString
import com.wf.bm.core.utils.toStringWithTwoDecimalsAndNoTrailingZeros

@Composable
fun TransactionDismissibleListItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    onRemove: (Transaction) -> Unit
) {
    SwipeLeftToDeleteBox(
        modifier = modifier,
        item = transaction,
        deleteItem = onRemove
    ) {
        TransactionListItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            transaction = transaction
        )
    }
}

@Composable
fun TransactionListItem(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineContent = {
            Text(
                text = transaction.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )
        },
        supportingContent = {
            Text(
                text = transaction.date.toFullDescriptionString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Light
            )
        },
        leadingContent = {
            Text(
                text = if (transaction.isPositive) "+ ${transaction.amount.toStringWithTwoDecimalsAndNoTrailingZeros()} ${transaction.currency.sign}"
                else "- ${transaction.amount.toStringWithTwoDecimalsAndNoTrailingZeros()} ${transaction.currency.sign}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                color = if (transaction.isPositive) Color.Green else Color.Red
            )
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
    )
}