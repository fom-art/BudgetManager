package com.wf.bm.core.designsystem.components.buttons

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOptionIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    // Convert String options into composable lambdas
    val composableOptions: List<@Composable () -> Unit> = options.mapIndexed { index, option ->
        @Composable {
            Text(
                text = option,
                color = if (index == selectedOptionIndex)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }

    // Use the original CustomToggleButton implementation
    CustomToggleButton(
        modifier = modifier,
        options = composableOptions,
        selectedOptionIndex = selectedOptionIndex,
        onOptionSelected = onOptionSelected
    )
}

@Composable
fun CustomToggleButton(
    modifier: Modifier = Modifier,
    options: List<@Composable () -> Unit>,
    selectedOptionIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    val transition = remember { Animatable(0f) }

    // Animate the selection position whenever the selectedOptionIndex changes
    LaunchedEffect(selectedOptionIndex) {
        transition.animateTo(
            targetValue = selectedOptionIndex.toFloat(),
            animationSpec = tween(durationMillis = 300)
        )
    }

    Layout(
        content = {
            // Animated selection background
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer, // Selected background color
                        shape = RoundedCornerShape(50)
                    )
            )

            // Options content
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { onOptionSelected(index) },
                            indication = ripple(bounded = true, radius = 48.dp),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    option()
                }
            }
        },
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant, // General background color
                shape = RoundedCornerShape(50)
            )
            .height(48.dp)
            .fillMaxWidth()
    ) { measurables, constraints ->
        val totalWidth = constraints.maxWidth
        val optionWidth = totalWidth / options.size
        val optionHeight = constraints.maxHeight

        // Measure children
        val backgroundPlaceable = measurables[0].measure(
            Constraints.fixed(optionWidth, optionHeight)
        )
        val optionPlaceables = measurables.drop(1).map {
            it.measure(
                Constraints.fixed(optionWidth, optionHeight)
            )
        }

        layout(totalWidth, optionHeight) {
            // Place animated background
            backgroundPlaceable.place(x = (transition.value * optionWidth).toInt(), y = 0)

            // Place options
            optionPlaceables.forEachIndexed { index, placeable ->
                val x = index * optionWidth
                placeable.place(x = x, y = 0)
            }
        }
    }
}

@Preview()
@Composable
fun ToggleButtonStringPreview() {
    var selectedOptionIndex by remember { mutableIntStateOf(0) }

    BudgetManagerTheme {
        ToggleButton(
            options = listOf("Projects", "Upcoming"),
            selectedOptionIndex = selectedOptionIndex,
            onOptionSelected = { selectedOptionIndex = it }
        )
    }
}

@Preview
@Composable
fun CustomToggleButtonPreview() {
    var selectedOptionIndex by remember { mutableIntStateOf(0) }

    val options = listOf<@Composable () -> Unit>(
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Projects",
                    color = if (selectedOptionIndex == 0)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (selectedOptionIndex == 0)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Upcoming",
                    color = if (selectedOptionIndex == 1)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = if (selectedOptionIndex == 1)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )

    BudgetManagerTheme {
        CustomToggleButton(
            options = options,
            selectedOptionIndex = selectedOptionIndex,
            onOptionSelected = { selectedOptionIndex = it }
        )
    }
}