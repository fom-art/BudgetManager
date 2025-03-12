package com.wf.bm.core.data.repository

import com.wf.bm.core.data.database.SettlementsDatabase
import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User

class SettlementsRepositoryImpl(
    private val settlementsDatabase: SettlementsDatabase
) : SettlementsRepository {

    override fun createSettlementForUser(settlement: Settlement, user: User) {
        val settlementWithUser = settlement.copy(debtor = user)
        settlementsDatabase.addSettlement(settlementWithUser)
    }

    override fun deleteSettlementForUser(settlement: Settlement, user: User) {
        settlementsDatabase.deleteSettlement(settlement)
    }

    override fun updateSettlementForUser(settlement: Settlement, user: User) {
        val settlementWithUser = settlement.copy(debtor = user)
        settlementsDatabase.updateSettlement(settlementWithUser)
    }
}