package com.wf.bm.core.data.repository

import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.User

interface SettlementsRepository {
    fun createSettlementForUser(settlement: Settlement, user: User)
    fun deleteSettlementForUser(settlement: Settlement, user: User)
    fun updateSettlementForUser(settlement: Settlement, user: User)
}