package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Parcelize
data class Transaction(
    val id: Long = 0,
    val title: String = "",
    val isPositive: Boolean = false,
    val amount: Double = 0.0,
    val currency: Currency = Currency.USD,
    @Contextual val date: LocalDateTime = LocalDateTime.now(),
    val categories: List<Category> = emptyList(),
    val repeatableTransaction: RepetitionPeriods? = null
): Parcelable