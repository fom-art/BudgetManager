package com.wf.bm.feature.transactions.create.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.designsystem.components.CategoryTag
import com.wf.bm.feature.transactions.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesLayout(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    updateTransaction: (Transaction) -> Unit
) {
    var showAddCategoryDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.categories),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(paddingMedium))
        FlowRow {
            transaction.categories.forEach { category ->
                CategoryTag(
                    title = category.title,
                    onClick = {
                        // Remove the clicked category
                        val updatedCategories = transaction.categories.filter { it != category }
                        updateTransaction(transaction.copy(categories = updatedCategories))
                    }
                )
            }
            CategoryTag(active = false, onClick = { showAddCategoryDialog = true }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.add_category),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    if (showAddCategoryDialog) {
        AddCategoryDialogue(
            onDismissRequest = { showAddCategoryDialog = false },
            onCategorySelected = { category ->
                val updatedCategories = transaction.categories + category
                updateTransaction(transaction.copy(categories = updatedCategories))
            }
        )
    }
}