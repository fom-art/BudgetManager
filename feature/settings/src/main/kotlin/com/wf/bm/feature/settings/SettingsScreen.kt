package com.wf.bm.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.compose.BudgetManagerTheme
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.User
import com.wf.bm.feature.settings.layouts.currency.PreferredCurrencyLayout
import com.wf.bm.feature.settings.layouts.SettingItem
import com.wf.bm.feature.settings.layouts.language.LanguageLayout

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    user: User,
    preferredCurrency: Currency,
    selectedLanguage: String,
    isDarkTheme: Boolean,
    changeAvatarPhoto: () -> Unit,
    changePreferredCurrency: (Currency) -> Unit,
    changeLanguage: (String) -> Unit,
    changeIsDarkTheme: (Boolean) -> Unit,
    logOut: () -> Unit,
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // Avatar Section
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxWidth(0.4f)
                .aspectRatio(1f)
                .clickable { changeAvatarPhoto() },
            placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
            fallback = rememberVectorPainter(Icons.Default.AccountCircle),
            model = user.photo,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )

        // Preferred Currency Section
        PreferredCurrencyLayout(
            preferredCurrency = preferredCurrency,
            onCurrencySelected = changePreferredCurrency
        )

        // Language Section
        LanguageLayout(
            selectedLanguage = selectedLanguage,
            onLanguageSelected = changeLanguage
        )

        // Color Theme Section
        SettingItem(
            title = stringResource(R.string.color_theme),
            value = if (isDarkTheme) "Dark" else "Light",
            onClick = { changeIsDarkTheme(!isDarkTheme) }
        )

        // Log Out Section
        Text(
            text = stringResource(R.string.log_out),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable { logOut() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    var preferredCurrency by remember { mutableStateOf(Currency.USD) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var isDarkTheme by remember { mutableStateOf(true) }

    BudgetManagerTheme(darkTheme = isDarkTheme) {
        SettingsScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            user = User(
                id = 1,
                name = "John Doe",
                nickname = "johndoe",
                photo = null
            ),
            preferredCurrency = preferredCurrency,
            selectedLanguage = selectedLanguage,
            isDarkTheme = isDarkTheme,
            changeAvatarPhoto = { println("Change Avatar Photo") },
            changePreferredCurrency = { newCurrency ->
                preferredCurrency = newCurrency
                println("Changed to: ${newCurrency.currencyNameRes}")
            },
            changeLanguage = { newLanguage ->
                selectedLanguage = newLanguage
                println("Language Selected: $newLanguage")
            },
            changeIsDarkTheme = { newTheme ->
                isDarkTheme = newTheme
                println("Dark Theme: $newTheme")
            },
            logOut = { println("Log Out") }
        )
    }
}