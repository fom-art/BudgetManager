package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class User(
    val id: Long = 0,
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val nickname: String = "",
    val photo: String? = null,
    val debtorSettlements: List<Settlement> = emptyList(),
    val creditorSettlements: List<Settlement> = emptyList(),
    val goals: List<Goal> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val friends: List<User> = emptyList(),
    val notifications: List<SettlementNotification> = emptyList()
) : Parcelable
