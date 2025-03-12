package com.wf.bm.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
@Parcelize
data class Settlement(
    val debtor: User = User(),
    val creditor: User = User(),
    val status: SettlementStatus = SettlementStatus.APPROVED,
    val amount: Double = 0.0,
    val currency: Currency = Currency.USD,
    @Contextual val creationDateTime: LocalDateTime = LocalDateTime.now()
): Parcelable {
    fun getSecondUser(firstUser: User): User {
        return if (creditor == firstUser) debtor else creditor
    }

    /**
     * Determines if the settlement is a loan (positive) or a debt (negative) for the given user.
     * A settlement is considered "positive" (a loan) if the user is the creditor, meaning
     * they are owed money. Otherwise, it is considered a "debt" (negative) if the user is the debtor.
     *
     * @param user The user for whom the settlement's nature is being determined.
     * @return True if the settlement is a loan for the user (creditor), false if it is a debt (debtor).
     */
    fun isLoanForUser(user: User): Boolean {
        return creditor == user
    }
}

enum class SettlementStatus {
    CANCELED, SEND, APPROVED, UNPAID, PAID, REJECTED
}