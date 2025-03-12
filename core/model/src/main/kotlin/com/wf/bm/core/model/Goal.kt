package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Parcelize
data class Goal(
    val name: String = "",
    val targetAmount: Double = 0.0,
    val progressAmount: Double = 0.0,
    val goalType: GoalType = GoalType.INCOME,
    @Contextual val dueDateTime: LocalDateTime = LocalDateTime.now(),
    val currency: Currency = Currency.USD,
): Parcelable

enum class GoalType {
    INCOME, EXPENSE;
}