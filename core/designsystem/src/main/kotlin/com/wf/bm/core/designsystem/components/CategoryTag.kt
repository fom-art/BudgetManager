package com.wf.bm.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategoryTag(
    modifier: Modifier = Modifier,
    title: String,
    active: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    CategoryTag(
        modifier = modifier,
        active = active,
        onClick = onClick
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CategoryTag(
    modifier: Modifier = Modifier,
    active: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val backgroundColor = if (active) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        Color.Transparent
    }
    Box(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 3.dp)
            .let { baseModifier ->
                if (!active) {
                    baseModifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    baseModifier
                }
            }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .let { baseModifier ->
                if (onClick != null) {
                    baseModifier.clickable { onClick() }
                } else {
                    baseModifier
                }
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        content()
    }
}