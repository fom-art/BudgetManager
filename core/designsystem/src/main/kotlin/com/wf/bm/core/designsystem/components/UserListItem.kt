package com.wf.bm.core.designsystem.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.designsystem.values.iconSizeLarge
import com.wf.bm.core.designsystem.values.iconSizeMedium
import com.wf.bm.core.model.User

@Composable
fun UserListItem(
    modifier: Modifier = Modifier,
    user: User,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    trailingContent: @Composable (() -> Unit)? = null
) {
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineContent = {
            Text(
                text = if (user.surname.isNotEmpty()) "${user.surname} ${user.name}" else user.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Normal
            )
        },
        supportingContent = {
            if (user.nickname.isNotEmpty()) {
                Text(
                    text = "@${user.nickname}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Light
                )
            }
        },
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(iconSizeLarge),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                fallback = rememberVectorPainter(Icons.Default.AccountCircle),
                model = user.photo,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        },
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(containerColor = containerColor),
    )
}

@Composable
fun UserListItemSmall(
    modifier: Modifier = Modifier,
    user: User,
    trailingContent: @Composable (() -> Unit)? = null
) {
    ListItem(
        modifier = modifier.fillMaxWidth(),
        headlineContent = {
            Text(
                text = if (user.surname.isNotEmpty()) "${user.surname} ${user.name}" else user.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal
            )
        },
        supportingContent = {
            if (user.nickname.isNotEmpty()) {
                Text(
                    text = "@${user.nickname}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Light
                )
            }
        },
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(iconSizeMedium),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                fallback = rememberVectorPainter(Icons.Default.AccountCircle),
                model = user.photo,
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        },
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
    )
}

@Composable
fun UserDismissibleItem(
    user: User,
    modifier: Modifier = Modifier,
    onRemove: (User) -> Unit
) {
    SwipeLeftToDeleteBox(modifier = modifier, item = user, deleteItem = onRemove) {
        UserListItem(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            user = user
        )
    }
}

@Preview
@Composable
fun SwipeToDismissUserItemPreview() {
    val user = User(
        id = 0,
        name = "Name",
        surname = "Surname",
        nickname = "nickname",
        photo = ""
    )

    val context = LocalContext.current

    BudgetManagerTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            UserDismissibleItem(
                onRemove = { user ->
                    Toast.makeText(
                        context,
                        "User ${user.name} was deleted.",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                user = user
            )
        }
    }
}

@Preview
@Composable
fun UserDisplayItemPreview() {
    val user = User(
        id = 0,
        name = "Name",
        surname = "Surname",
        nickname = "nickname",
        photo = ""
    )

    BudgetManagerTheme {
        UserListItem(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            user = user
        )
    }
}