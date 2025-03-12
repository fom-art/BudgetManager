package com.wf.bm.feature.settlements.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wf.bm.core.designsystem.values.iconSizeLarge
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User

@Composable
fun SettlementNotificationsListItem(
    modifier: Modifier = Modifier,
    user: User,
    settlement: Settlement,
    submitNotification: (Settlement) -> Unit,
    rejectNotification: (Settlement) -> Unit,
) {
    val friend = settlement.getSecondUser(user)
    ListItem(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(iconSizeLarge),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                fallback = rememberVectorPainter(Icons.Default.AccountCircle),
                model = friend.photo,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        },
        headlineContent = {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("@${friend.nickname}")
                    }
                    append(" sent you a ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append(if (settlement.isLoanForUser(user)) "loan" else "debt")
                    }
                    append(" request for ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = if (settlement.isLoanForUser(user)) Color.Green else Color.Red
                        )
                    ) {
                        append("${settlement.amount} ${settlement.currency.sign}")
                    }
                },
                style = MaterialTheme.typography.labelLarge
            )
        },
        trailingContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { submitNotification(settlement) },
                    modifier = Modifier
                        .size(iconSizeLarge)
                        .background(Color.Green)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Accept Notification",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { rejectNotification(settlement) },
                    modifier = Modifier
                        .size(iconSizeLarge)
                        .background(Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Reject Notification",
                        tint = Color.White
                    )
                }
            }
        }
    )
}