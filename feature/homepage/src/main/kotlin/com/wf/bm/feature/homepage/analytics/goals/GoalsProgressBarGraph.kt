package com.wf.bm.feature.homepage.analytics.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.GoalType
import java.time.LocalDateTime

@Composable
fun GoalsProgressBarGraph(
    modifier: Modifier = Modifier,
    goals: List<Goal>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // Goals List
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            goals.forEach { goal ->
                ProgressBarWithLabel(
                    label = goal.name,
                    progress = (goal.progressAmount / goal.targetAmount).coerceIn(0.0, 1.0),
                    color = if (goal.goalType == GoalType.INCOME) {
                        Color.Green.copy(alpha = 0.5f)
                    } else {
                        Color.Red.copy(alpha = 0.5f)
                    }
                )
            }
        }
    }
}

@Composable
private fun ProgressBarWithLabel(
    label: String,
    progress: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label with flexible width and ellipsis
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis, // Add ellipsis if text is too long
            modifier = Modifier
                .width(60.dp)
        )

        // Progress Bar
        Spacer(modifier = Modifier.width(8.dp)) // Add spacing between label and progress bar
        Box(
            modifier = Modifier
                .weight(1f)
                .height(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress.toFloat())
                    .background(color)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GoalProgressBarPreview() {
    val goals = listOf(
        Goal(
            name = "Vacation",
            targetAmount = 1000.0,
            progressAmount = 400.0,
            goalType = GoalType.EXPENSE,
            dueDateTime = LocalDateTime.now().plusMonths(3),
            currency = Currency.USD
        ),
        Goal(
            name = "Car",
            targetAmount = 5000.0,
            progressAmount = 1200.0,
            goalType = GoalType.EXPENSE,
            dueDateTime = LocalDateTime.now().plusYears(1),
            currency = Currency.USD
        ),
        Goal(
            name = "Savings",
            targetAmount = 2000.0,
            progressAmount = 1500.0,
            goalType = GoalType.INCOME,
            dueDateTime = LocalDateTime.now().plusWeeks(6),
            currency = Currency.EUR
        ),
        Goal(
            name = "Education",
            targetAmount = 3000.0,
            progressAmount = 800.0,
            goalType = GoalType.EXPENSE,
            dueDateTime = LocalDateTime.now().plusYears(2),
            currency = Currency.USD
        ),
        Goal(
            name = "Emergency Fund",
            targetAmount = 10000.0,
            progressAmount = 5000.0,
            goalType = GoalType.INCOME,
            dueDateTime = LocalDateTime.now().plusMonths(6),
            currency = Currency.USD
        )
    )

    BudgetManagerTheme {
        GoalsProgressBarGraph(goals = goals)
    }
}
