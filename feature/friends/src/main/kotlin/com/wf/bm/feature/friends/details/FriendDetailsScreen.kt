package com.wf.bm.feature.friends.details

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton
import com.wf.bm.core.designsystem.components.DefaultTopBar
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.User

@Composable
fun FriendDetailsScreen(
    modifier: Modifier = Modifier,
    user: User,
    totalDebt: Int,
    totalDebtCurrency: Currency,
    closeTotalDebt: () -> Unit,
    deleteFriend: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DefaultTopBar(
                title = "",
                goBack = goBack,
                actions = {
                    IconButton(onClick = deleteFriend) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(
                                com.wf.bm.core.designsystem.R.string.delete
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()){
            AnimatedGradientBackground()
            FriendDetailsBody(
                modifier = Modifier.padding(innerPadding).align(Alignment.Center),
                user = user,
                totalDebt = totalDebt,
                totalDebtCurrency = totalDebtCurrency,
                closeTotalDebt = closeTotalDebt
            )
        }
    }
}

@Composable
fun FriendDetailsBody(
    modifier: Modifier = Modifier,
    user: User,
    totalDebt: Int,
    totalDebtCurrency: Currency,
    closeTotalDebt: () -> Unit,
) {
        Card (modifier = modifier){
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.8f),
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
                    model = user.photo,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Text(
                    text = "@${user.nickname}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.padding_large)))
                Text(
                    text = if (user.surname.isNotEmpty()) "${user.surname} ${user.name}" else user.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.padding_large)))
                Text(
                    text = stringResource(com.wf.bm.feature.friends.R.string.total_debt),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Text(
                    text = "$totalDebt ${totalDebtCurrency.sign}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal
                )
                Spacer(Modifier.height(dimensionResource(R.dimen.padding_large)))
                BmFilledButton(
                    onClick = closeTotalDebt,
                    text = stringResource(com.wf.bm.feature.friends.R.string.close_total_debt)
                )
            }
        }
}

@Preview
@Composable
fun FriendDetailsScreenPreview() {
    BudgetManagerTheme {
        val user = User(
            id = 0,
            name = "John",
            surname = "Doe",
            nickname = "johndoe",
            photo = ""
        )

        FriendDetailsScreen(
            user = user,
            totalDebt = 40,
            totalDebtCurrency = Currency.UAH,
            closeTotalDebt = {},
            deleteFriend = {},
            goBack = {}
        )
    }
}