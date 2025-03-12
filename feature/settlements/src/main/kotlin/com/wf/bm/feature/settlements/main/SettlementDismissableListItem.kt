package com.wf.bm.feature.settlements.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.wf.bm.core.designsystem.components.SwipeLeftToDeleteBox
import com.wf.bm.core.designsystem.values.iconSizeLarge
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User
import com.wf.bm.core.utils.toStringWithTwoDecimalsAndNoTrailingZeros

@Composable
fun TransactionDismissibleListItem(
    modifier: Modifier = Modifier,
    user: User,
    settlement: Settlement,
    onRemove: (Settlement) -> Unit
) {
    SwipeLeftToDeleteBox(
        modifier = modifier,
        item = settlement,
        deleteItem = onRemove
    ) {
        SettlementListItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            settlement = settlement,
            user = user
        )
    }
}

@Composable
fun SettlementListItem(
    modifier: Modifier = Modifier,
    user: User,
    settlement: Settlement
) {
    val secondUser = settlement.getSecondUser(firstUser = user)
    ListItem(
        modifier = modifier.fillMaxWidth(),
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(iconSizeLarge),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                fallback = rememberVectorPainter(Icons.Default.AccountCircle),
                model = user.photo,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        },
        headlineContent = {
            Text(
                text = secondUser.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )
        },
        supportingContent = {
            Text(
                text = secondUser.nickname,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Light
            )
        },
        trailingContent = {
            Text(
                text = if (settlement.isLoanForUser(user)) "+ ${settlement.amount.toStringWithTwoDecimalsAndNoTrailingZeros()} ${settlement.currency.sign}"
                else "- ${settlement.amount.toStringWithTwoDecimalsAndNoTrailingZeros()} ${settlement.currency.sign}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal,
                color = if (settlement.isLoanForUser(user)) Color.Green else Color.Red
            )
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
    )
}

