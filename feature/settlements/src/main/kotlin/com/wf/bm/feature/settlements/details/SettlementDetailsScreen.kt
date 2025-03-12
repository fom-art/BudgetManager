package com.wf.bm.feature.settlements.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.designsystem.values.paddingLarge
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import com.wf.bm.core.utils.toFullDescriptionString
import com.wf.bm.feature.settlements.R
import java.time.LocalDateTime

@Composable
fun SettlementDetailsScreen(
    modifier: Modifier = Modifier,
    user: User,
    settlement: Settlement,
    goBack: () -> Unit,
    deleteSettlement: (Settlement) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                goBack = goBack,
                actions = {
                    IconButton(onClick = { deleteSettlement(settlement) }) {
                        Icon(
                            imageVector = Icons.Default.Delete, contentDescription = stringResource(
                                com.wf.bm.core.designsystem.R.string.delete
                            )
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedGradientBackground()
            SettlementDetailsBody(
                modifier = Modifier
                    .padding(innerPadding)
                    .align(Alignment.Center),
                user = user,
                settlement = settlement
            )
        }
    }
}

@Composable
fun SettlementDetailsBody(
    modifier: Modifier = Modifier,
    user: User,
    settlement: Settlement,
) {
    val isLoan = settlement.isLoanForUser(user)
    val friend = settlement.getSecondUser(user)
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .padding(vertical = paddingMedium)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                fallback = rememberVectorPainter(Icons.Default.AccountCircle),
                model = friend.photo,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.height(dimensionResource(com.wf.bm.core.designsystem.R.dimen.padding_medium)))
            if (friend.nickname.isNotEmpty()) {
                Text(
                    text = "@${friend.nickname}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(Modifier.height(paddingLarge))
            Text(
                text = if (friend.surname.isNotEmpty()) "${friend.surname} ${friend.name}" else friend.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(paddingLarge))
            Text(
                text = stringResource(R.string.time),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(paddingMedium))
            Text(
                text = settlement.creationDateTime.toFullDescriptionString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(paddingLarge))
            Text(
                text = if (isLoan) stringResource(R.string.loan) else stringResource(R.string.debt),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(paddingMedium))
            Text(
                text = "${settlement.amount} ${settlement.currency.sign}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = if (isLoan) Color.Green else Color.Red
            )
            Spacer(Modifier.height(paddingLarge))
            Text(
                text = stringResource(R.string.status),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(paddingMedium))
            Text(
                text = settlement.status.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(paddingLarge))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DebtDetailsScreenPreview() {
    val user = User(1, "John", "Doe", "johndoe")
    val friend = User(id = 2, name = "Jane", surname = "Smith", nickname = "janesmith")
    val mockSettlement = Settlement(
        debtor = user,
        creditor = friend,
        status = SettlementStatus.SEND,
        amount = 200.0,
        currency = Currency.USD,
        creationDateTime = LocalDateTime.now()
    )

    BudgetManagerTheme {
        SettlementDetailsScreen(
            user = user,
            settlement = mockSettlement,
            goBack = { /* No-op */ },
            deleteSettlement = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoanDetailsScreenPreview() {
    val user = User(1, "John", "Doe", "johndoe")
    val friend = User(id = 2, name = "Jane", surname = "Smith", nickname = "janesmith")
    val mockSettlement = Settlement(
        debtor = friend,
        creditor = user,
        status = SettlementStatus.SEND,
        amount = 200.0,
        currency = Currency.USD,
        creationDateTime = LocalDateTime.now()
    )

    BudgetManagerTheme {
        SettlementDetailsScreen(
            user = user,
            settlement = mockSettlement,
            goBack = { /* No-op */ },
            deleteSettlement = {}
        )
    }
}