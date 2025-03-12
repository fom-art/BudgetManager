package com.wf.bm.core.designsystem.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.spring
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme

@Composable
fun AnimatedToggleButton(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    text: String,
) {
    // Animating colors
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) MaterialTheme.colorScheme.primary else Color.Transparent,
        animationSpec = spring(), label = ""
    )

    val contentColor by animateColorAsState(
        targetValue = if (isChecked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        animationSpec = spring(), label = ""
    )

    val borderColor by animateColorAsState(
        targetValue = if (isChecked) Color.Transparent else MaterialTheme.colorScheme.outline,
        animationSpec = spring(), label = ""
    )

    // Switching between Button and OutlinedButton
    val onClick = { onCheckedChange() }
    if (isChecked) {
        // Filled Button
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            modifier = modifier.padding(4.dp)
        ) {
            Text(text, color = contentColor)
        }
    } else {
        // Outlined Button
        OutlinedButton(
            onClick = onClick,
            border = BorderStroke(2.dp, borderColor),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = contentColor),
            modifier = modifier.padding(4.dp)
        ) {
            Text(text, color = contentColor)
        }
    }
}

@Preview
@Composable
fun AnimatedToggleButtonPreview() {
    BudgetManagerTheme {
        var isChecked by remember { mutableStateOf(false) }

        AnimatedToggleButton(
            isChecked = isChecked,
            onCheckedChange = { isChecked = !isChecked },
            text = "Button"
        )
    }
}