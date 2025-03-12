package com.wf.bm.core.designsystem.layouts

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wf.bm.core.designsystem.R

@Composable
fun TransactionPositivityIcon(
    modifier: Modifier = Modifier,
    isPositive: Boolean,
    changePositivity: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .clickable(onClick = { changePositivity(!isPositive) })
    ) {
        if (isPositive) {
            Icon(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                imageVector = Icons.Default.Add,
                tint = Color.Green,
                contentDescription = stringResource(R.string.income_icon_description),
            )
        } else {
            Icon(
                modifier = Modifier.fillMaxSize().align(Alignment.Center),
                imageVector = Icons.Default.Remove,
                tint = Color.Red,
                contentDescription = stringResource(R.string.outcome_icon_description),
            )
        }
    }
}