package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.Period

@Serializable
@Parcelize
data class RecurringPayment(
    val amount: Double = 0.0,
    val name: String = "",
    val description: String = "",
    val category: Category = Category(),
    @Contextual val frequency: Period = Period.ZERO,
    @Contextual val lastPaymentDate: LocalDateTime = LocalDateTime.now(),
    val status: RecurringPaymentStatus = RecurringPaymentStatus.PAUSED,
    val currency: Currency = Currency.USD,
    val id: Long = 0
): Parcelable

enum class RecurringPaymentStatus() {
    ACTIVE, PAUSED;
}
