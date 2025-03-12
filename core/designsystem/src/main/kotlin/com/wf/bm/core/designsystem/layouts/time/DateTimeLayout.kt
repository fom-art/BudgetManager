package com.wf.bm.core.designsystem.layouts.time

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
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
import com.wf.bm.core.designsystem.R
import com.wf.bm.core.designsystem.values.paddingMedium
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.utils.toDateDescriptionString
import com.wf.bm.core.utils.toTimeDescriptionString
import java.time.LocalDateTime

@Composable
fun DateTimeLayout(
    modifier: Modifier = Modifier,
    date: LocalDateTime,
    updateDate: (LocalDateTime) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Call the DateLayout
            DateLayout(
                date = date,
                updateDate = { selectedDate ->
                    updateDate(date.with(selectedDate))
                }
            )

            Spacer(Modifier.width(paddingMedium))

            // Call the TimeLayout
            TimeLayout(
                modifier = Modifier.weight(1f),
                date = date,
                updateDate = { selectedTime ->
                    val updatedDateTime = date
                        .withHour(selectedTime.hour)
                        .withMinute(selectedTime.minute)
                    updateDate(updatedDateTime)
                }
            )
        }
    }
}


@Composable
fun DateLayout(
    modifier: Modifier = Modifier,
    date: LocalDateTime,
    updateDate: (LocalDateTime) -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable { showDatePickerDialog = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                .padding(vertical = 2.dp, horizontal = 4.dp),
            text = date.toDateDescriptionString(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.width(paddingMedium))
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

    if (showDatePickerDialog) {
        DatePickerDialogContent(
            initialDate = date,
            onDateSelected = { selectedDate ->
                updateDate(date.with(selectedDate))
                showDatePickerDialog = false
            },
            onDismiss = { showDatePickerDialog = false }
        )
    }
}


@Composable
fun TimeLayout(
    modifier: Modifier = Modifier,
    date: LocalDateTime,
    updateDate: (LocalDateTime) -> Unit
) {
    var showTimePickerDialog by remember { mutableStateOf(false) }

    Text(
        modifier = modifier
            .clickable { showTimePickerDialog = true }
            ,
        text = date.toTimeDescriptionString(),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium
    )

    if (showTimePickerDialog) {
        TimePickerDialog(
            initialTime = date.toLocalTime(),
            onTimeSelected = { selectedTime ->
                val updatedDateTime = date
                    .withHour(selectedTime.hour)
                    .withMinute(selectedTime.minute)
                updateDate(updatedDateTime)
                showTimePickerDialog = false
            },
            onDismissRequest = { showTimePickerDialog = false }
        )
    }
}