package com.wf.bm.feature.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Category
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.GoalType
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import com.wf.bm.feature.homepage.analytics.goals.GoalsProgressBarGraph
import com.wf.bm.feature.homepage.analytics.piechart.TransactionsByCategoriesPieChart
import java.time.LocalDateTime

@Composable
fun HomepageScreen(
    modifier: Modifier = Modifier,
    user: User,
    preferredCurrency: Currency,
    expenses: List<Transaction>,
    goals: List<Goal>,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(paddingMedium)
    ) {
        Spacer(Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.welcome, user.name),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (expenses.isNotEmpty() || goals.isNotEmpty()) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (expenses.isNotEmpty()) {
                    Spacer(Modifier.height(32.dp))

                    TransactionsByCategoriesPieChart(
                        transactions = expenses,
                        currency = preferredCurrency
                    )
                }
                if (goals.isNotEmpty()) {
                    Spacer(Modifier.height(32.dp))

                    Text(
                        text = stringResource(R.string.goals),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    GoalsProgressBarGraph(
                        goals = goals
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SentimentDissatisfied,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_data_message),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomepageScreenPreview() {
    val user = User(
        name = "John"
    )
    val sampleTransactions = listOf(
        Transaction(
            title = "Groceries",
            isPositive = false,
            amount = 150.0,
            currency = Currency.USD,
            date = LocalDateTime.now(),
            categories = listOf(Category("Food")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Rent",
            isPositive = false,
            amount = 800.0,
            currency = Currency.EUR,
            date = LocalDateTime.now(),
            categories = listOf(Category("Housing")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Utilities",
            isPositive = false,
            amount = 200.0,
            currency = Currency.UAH,
            date = LocalDateTime.now(),
            categories = listOf(Category("Utilities")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Dining Out",
            isPositive = false,
            amount = 100.0,
            currency = Currency.CZK,
            date = LocalDateTime.now(),
            categories = listOf(Category("Dining")),
            repeatableTransaction = null
        ),
        Transaction(
            title = "Entertainment",
            isPositive = false,
            amount = 300.0,
            currency = Currency.USD,
            date = LocalDateTime.now(),
            categories = listOf(Category("Leisure")),
            repeatableTransaction = null
        )
    )

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
        HomepageScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            user = user,
            preferredCurrency = Currency.UAH,
            expenses = sampleTransactions,
            goals = goals,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyHomepageScreenPreview() {
    val user = User(
        name = "John"
    )

    BudgetManagerTheme {
        HomepageScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            user = user,
            preferredCurrency = Currency.UAH,
            expenses = emptyList(),
            goals = emptyList(),
        )
    }
}