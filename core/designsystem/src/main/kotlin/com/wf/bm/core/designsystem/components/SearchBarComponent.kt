package com.wf.bm.core.designsystem.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    expanded: Boolean,
    placeHolder: String,
    onExpandedChange: (Boolean) -> Unit,
    clearSearch: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    SearchBar(
        modifier = modifier
            .semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = textFieldState.text.toString(),
                onQueryChange = textFieldState::setTextAndPlaceCursorAtEnd,
                onSearch = { onExpandedChange(false) },
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = { Text(text = placeHolder) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (textFieldState.text.isNotEmpty()) {
                        IconButton(onClick = clearSearch) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }
                },
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        content = content
    )
}