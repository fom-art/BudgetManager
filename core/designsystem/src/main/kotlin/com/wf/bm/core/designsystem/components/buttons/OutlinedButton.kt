package com.wf.bm.core.designsystem.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.R



@Composable
fun BmOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    BmOutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = dimensionResource(id = R.dimen.padding_xsmall),
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                ),
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun BmOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    OutlinedButton (
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        content()
    }
}


@Preview
@Composable
fun BmOutlinedButtonPreview() {
    var isEnabled by remember { mutableStateOf(true) }
    BudgetManagerTheme {
        BmOutlinedButton(
            text = "Click me!",
            onClick = { isEnabled = !isEnabled },
            enabled = isEnabled
        )
    }
}