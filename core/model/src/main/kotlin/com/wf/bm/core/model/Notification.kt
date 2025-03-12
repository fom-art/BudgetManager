package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class SettlementNotification(
    val friend: User,
    val amount: Double
): Parcelable