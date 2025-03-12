package com.wf.bm.core.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wf.bm.core.designsystem.R

@Composable
fun NotificationIconWithBadge(
    modifier: Modifier = Modifier,
    notificationCount: Int
) {
    BadgedBox(
        modifier = modifier,
        badge = {
            if (notificationCount > 0) {
                Badge(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Text(
                        text = notificationCount.toString(),
                        modifier = Modifier.padding(1.dp),
                        fontSize = 10.sp
                    )
                }
            }
        }
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = stringResource(R.string.notifications)
        )
    }
}
