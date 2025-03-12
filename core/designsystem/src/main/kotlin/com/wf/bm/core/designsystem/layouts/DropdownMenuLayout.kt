package com.wf.bm.core.designsystem.layouts

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.unit.dp
import com.wf.bm.core.designsystem.values.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuLayout(
    modifier: Modifier = Modifier,
    selectedItem: String,
    items: List<Int>,
    selectAnItem: (Int) -> Unit,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clickable {}
            .border(
                width = 1.dp, color = if (enabled) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
            )
            .padding(horizontal = paddingSmall, vertical = 4.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (enabled) expanded = !expanded }) {
            // Anchor for the dropdown menu
            Row(
                modifier = Modifier.menuAnchor(
                    type = MenuAnchorType.PrimaryEditable,
                    enabled = true
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedItem,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Spacer(Modifier.width(paddingSmall))

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = if (enabled) MaterialTheme.colorScheme.onBackground
                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }


            // Dropdown menu items
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(stringResource(item)) },
                        onClick = {
                            selectAnItem(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}