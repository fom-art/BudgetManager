package com.wf.bm.feature.goals.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.components.SwipeLeftToDeleteBox
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton
import com.wf.bm.core.designsystem.layouts.expenses.ExpensesTextField
import com.wf.bm.core.designsystem.values.iconSizeMedium
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.GoalType
import com.wf.bm.core.utils.toDateDescriptionString
import java.time.LocalDateTime

@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier,
    goals: List<Goal>,
    updateGoal: (Goal) -> Unit,
    deleteGoal: (Goal) -> Unit,
    goBack: () -> Unit,
    goToAddGoal: () -> Unit,
    goToGoalDetails: (Goal) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = stringResource(com.wf.bm.core.designsystem.R.string.goals),
                goBack = goBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = goToAddGoal,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(com.wf.bm.core.designsystem.R.string.add)
                )
            }
        }
    ) { innerPadding ->
        GoalsLayout(
            modifier = Modifier.padding(innerPadding),
            goals = goals,
            updateGoal = updateGoal,
            deleteGoal = deleteGoal,
            goToGoalDetails = goToGoalDetails
        )
    }
}

@Composable
fun GoalsLayout(
    modifier: Modifier = Modifier,
    goals: List<Goal>,
    updateGoal: (Goal) -> Unit,
    deleteGoal: (Goal) -> Unit,
    goToGoalDetails: (Goal) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(goals) { goal ->
            SwipeLeftToDeleteBox(item = goal, deleteItem = deleteGoal) {
                GoalExpandableListItem(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    goal = goal,
                    updateGoal = updateGoal,
                    goToGoalDetails = goToGoalDetails
                )
            }
        }
    }
}

@Composable
fun GoalExpandableListItem(
    modifier: Modifier = Modifier,
    goal: Goal,
    updateGoal: (Goal) -> Unit,
    goToGoalDetails: (Goal) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var addAmountTextFieldValue by remember { mutableDoubleStateOf(0.0) }
    Column(modifier = modifier
        .animateContentSize()
        .clickable { isExpanded = !isExpanded }) {
        GoalListItem(goal = goal)
        if (isExpanded) {
            Spacer(Modifier.height(8.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)){
                Text(
                    text = "${goal.progressAmount} ${goal.currency.sign} / ${goal.targetAmount} ${goal.currency.sign}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ExpensesTextField(
                        modifier = Modifier.fillMaxWidth(0.4f),
                        amount = addAmountTextFieldValue,
                        updateAmount = { newAmount -> addAmountTextFieldValue = newAmount }
                    )
                    Spacer(Modifier.width(4.dp))
                    IconButton(
                        onClick = {
                            updateGoal(goal.copy(progressAmount = goal.progressAmount + addAmountTextFieldValue))
                            addAmountTextFieldValue = 0.0
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(iconSizeMedium),
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = stringResource(com.wf.bm.core.designsystem.R.string.add)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    BmFilledButton(
                        onClick = { goToGoalDetails(goal) },
                        text = stringResource(com.wf.bm.core.designsystem.R.string.edit)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun GoalListItem(
    modifier: Modifier = Modifier,
    goal: Goal,
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Text(
                text = goal.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )
        },
        headlineContent = {
            Text(
                text = buildAnnotatedString {
                    if (goal.goalType == GoalType.INCOME) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Save up ")
                        }
                        append("to ")
                        withStyle(SpanStyle(color = Color.Green)) {
                            append("${goal.targetAmount} ${goal.currency.sign}")
                        }
                        append(" until ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(goal.dueDateTime?.toDateDescriptionString())
                        }
                    } else {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Spend ")
                        }
                        append("no more than ")
                        withStyle(SpanStyle(color = Color.Red)) {
                            append("${goal.targetAmount} ${goal.currency.sign}")
                        }
                        append(" until ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(goal.dueDateTime?.toDateDescriptionString())
                        }
                    }
                },
                style = MaterialTheme.typography.labelLarge,
            )
        }
    )
}

@Preview
@Composable
fun GoalsScreenPreview() {
    val sampleGoals = remember {
        mutableStateOf(
            listOf(
                Goal(
                    name = "Vacation Fund",
                    targetAmount = 5000.0,
                    goalType = GoalType.INCOME,
                    dueDateTime = LocalDateTime.now().plusMonths(6),
                    currency = Currency.USD,
                    progressAmount = 1200.0
                ),
                Goal(
                    name = "New Laptop",
                    targetAmount = 2000.0,
                    goalType = GoalType.EXPENSE,
                    dueDateTime = LocalDateTime.now().plusMonths(3),
                    currency = Currency.USD,
                    progressAmount = 500.0
                )
            )
        )
    }

    BudgetManagerTheme {
        GoalsScreen(
            goals = sampleGoals.value,
            updateGoal = { updatedGoal ->
                sampleGoals.value = sampleGoals.value.map { goal ->
                    if (goal.name == updatedGoal.name) updatedGoal else goal
                }
            },
            deleteGoal = { goalToDelete ->
                sampleGoals.value = sampleGoals.value.filter { it.name != goalToDelete.name }
            },
            goBack = { /* Navigate back */ },
            goToAddGoal = { /* Add new goal */ },
            goToGoalDetails = { goal -> /* Navigate to goal details */ }
        )
    }
}