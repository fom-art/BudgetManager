package com.wf.bm.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@Composable
fun LocalDateTime.toFullDescriptionString(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val preciseDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())

    return when {
        this.toLocalDate() == now.toLocalDate() -> {
            stringResource(R.string.today_at_time, this.format(formatter))
        }
        this.toLocalDate() == now.minus(1, ChronoUnit.DAYS).toLocalDate() -> {
            stringResource(R.string.yesterday_at_time, this.format(formatter))
        }
        else -> {
            this.format(preciseDateFormatter)
        }
    }
}

@Composable
fun LocalDateTime.toTimeDescriptionString(): String {
    val preciseDateFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

    return this.format(preciseDateFormatter)
}


@Composable
fun LocalDateTime.toDateDescriptionString(): String {
    val now = LocalDateTime.now()
    val preciseDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

    return when {
        this.toLocalDate() == now.toLocalDate() -> {
            stringResource(R.string.today)
        }
        this.toLocalDate() == now.minus(1, ChronoUnit.DAYS).toLocalDate() -> {
            stringResource(R.string.yesterday)
        }
        else -> {
            this.format(preciseDateFormatter)
        }
    }
}