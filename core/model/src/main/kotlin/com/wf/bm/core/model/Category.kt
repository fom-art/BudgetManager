package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    val title: String = "",
    val transactions: List<Transaction> = emptyList(),
    val recurringPayments: List<RecurringPayment> = emptyList(),
): Parcelable
