package com.wf.bm.core.model

import java.time.Period

data class RepeatableTransaction(
    val titleRes: Int,
    val repetitionInterval: Period
)

enum class RepetitionPeriods(val periodNameRes: Int, val period: Period) {
    DAY(R.string.day, period = Period.ofDays(1)),
    WEEK(R.string.week, period = Period.ofWeeks(1)),
    MONTH(R.string.month, period = Period.ofMonths(1)),
    YEAR(R.string.year, period = Period.ofYears(1));

    companion object {
        fun findRepetitionPeriodFromRes(resId: Int): RepetitionPeriods? {
            return RepetitionPeriods.entries.find { it.periodNameRes == resId }
        }
    }
}