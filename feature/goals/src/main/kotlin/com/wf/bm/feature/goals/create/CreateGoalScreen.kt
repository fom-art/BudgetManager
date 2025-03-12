package com.wf.bm.feature.goals.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.components.buttons.ToggleButton
import com.wf.bm.core.designsystem.layouts.expenses.ExpensesLayout
import com.wf.bm.core.designsystem.layouts.time.DateLayout
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.GoalType
import com.wf.bm.feature.goals.R
import java.time.LocalDateTime

@Composable
fun CreateGoalScreen(
    modifier: Modifier = Modifier,
    goal: Goal,
    updateGoal: (Goal) -> Unit,
    submit: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(goBack = goBack)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedGradientBackground()
            CreateGoalLayout(
                modifier = modifier
                    .padding(innerPadding)
                    .align(Alignment.Center),
                goal = goal,
                updateGoal = updateGoal,
                submit = submit
            )
        }
    }
}

@Composable
fun CreateGoalLayout(
    modifier: Modifier = Modifier,
    goal: Goal,
    updateGoal: (Goal) -> Unit,
    submit: () -> Unit,
) {
    val isExpense = goal.goalType == GoalType.EXPENSE
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = goal.name,
                onValueChange = { newName ->
                    updateGoal(goal.copy(name = newName))
                },
                textStyle = MaterialTheme.typography.headlineMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                )
            )

            val options = listOf(
                stringResource(R.string.spend),
                stringResource(R.string.save_up),
            )
            ToggleButton(
                options = options,
                selectedOptionIndex = if (isExpense) 0 else 1,
                onOptionSelected = { index ->
                    if (index == 0) updateGoal(goal.copy(goalType = GoalType.EXPENSE)) else
                        updateGoal(goal.copy(goalType = GoalType.INCOME))
                }
            )

            ExpensesLayout(
                modifier = Modifier.padding(horizontal = 16.dp),
                amount = goal.targetAmount,
                currency = goal.currency,
                updateAmount = { newAmount -> updateGoal(goal.copy(targetAmount = newAmount)) },
                updateCurrency = { newCurrency -> updateGoal(goal.copy(currency = newCurrency)) }
            )

            DueDateLayout(
                goal = goal,
                updateGoal = updateGoal
            )

            Button(
                onClick = submit,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(com.wf.bm.core.designsystem.R.string.done),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun DueDateLayout(
    modifier: Modifier = Modifier,
    goal: Goal,
    updateGoal: (Goal) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.due_date),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(8.dp))
        DateLayout(
            modifier = Modifier.fillMaxWidth(),
            date = goal.dueDateTime,
            updateDate = { newDate -> updateGoal(goal.copy(dueDateTime = newDate)) }
        )
    }
}

@Preview
@Composable
fun CreateGoalScreenPreview() {
    val sampleGoal = remember {
        mutableStateOf(
            Goal(
                name = "",
                targetAmount = 0.0,
                goalType = GoalType.EXPENSE,
                dueDateTime = LocalDateTime.now().plusMonths(3),
                currency = Currency.USD,
                progressAmount = 200.0
            )
        )
    }

    BudgetManagerTheme {
        CreateGoalScreen(
            goal = sampleGoal.value,
            updateGoal = { updatedGoal -> sampleGoal.value = updatedGoal },
            submit = { /* Handle submission logic */ },
            goBack = { /* Handle navigation back */ }
        )
    }
}