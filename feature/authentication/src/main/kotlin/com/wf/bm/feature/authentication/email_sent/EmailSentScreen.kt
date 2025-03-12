package com.wf.bm.feature.authentication.email_sent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.components.AnimatedGradientBackground
import com.wf.bm.core.designsystem.components.buttons.BmFilledButton

@Composable
fun EmailSentScreen(
    modifier: Modifier = Modifier,
    email: String,
    goToSignIn: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Gradient Background
        AnimatedGradientBackground()

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(R.dimen.padding_small),
                            vertical = dimensionResource(R.dimen.padding_xlarge)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(com.wf.bm.feature.authentication.R.string.email_was_sent_title),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    Text(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = dimensionResource(R.dimen.padding_small)),
                        text = stringResource(com.wf.bm.feature.authentication.R.string.email_was_sent_description, email),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                    BmFilledButton(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        text = stringResource(com.wf.bm.feature.authentication.R.string.sign_in),
                        onClick = goToSignIn,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EmailSentScreenPreview() {
    BudgetManagerTheme {
        EmailSentScreen(
            email = "example@gmail.com",
            goToSignIn = {}
        )
    }
}
