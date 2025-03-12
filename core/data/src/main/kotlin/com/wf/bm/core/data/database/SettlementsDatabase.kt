package com.wf.bm.core.data.database

import com.wf.bm.core.model.Settlement
import com.wf.bm.core.model.SettlementStatus
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import kotlin.random.Random

class SettlementsDatabase(private val usersDatabase: UsersDatabase) {

    private val _settlementsFlow = MutableStateFlow<List<Settlement>>(generateSettlements())
    val settlementsFlow: StateFlow<List<Settlement>> = _settlementsFlow

    private fun generateSettlements(): List<Settlement> {
        val users = usersDatabase.users
        val settlements = mutableListOf<Settlement>()
        for (i in users.indices) {
            for (j in users.indices) {
                if (i != j) {
                    val user1 = users[i]
                    val user2 = users[j]
                    repeat(Random.nextInt(4, 6)) {
                        settlements.add(
                            Settlement(
                                debtor = if (it % 2 == 0) user1 else user2,
                                creditor = if (it % 2 == 0) user2 else user1,
                                status = SettlementStatus.values().random(),
                                amount = Random.nextDouble(10.0, 1000.0),
                                creationDateTime = LocalDateTime.now().minusDays(Random.nextLong(0, 30))
                            )
                        )
                    }
                }
            }
        }
        return settlements
    }

    fun addSettlement(settlement: Settlement) {
        val updatedSettlements = _settlementsFlow.value + settlement
        _settlementsFlow.value = updatedSettlements
    }

    fun updateSettlement(settlement: Settlement) {
        val updatedSettlements = _settlementsFlow.value.map {
            if (it == settlement) settlement else it
        }
        _settlementsFlow.value = updatedSettlements
    }

    fun deleteSettlement(settlement: Settlement) {
        val updatedSettlements = _settlementsFlow.value - settlement
        _settlementsFlow.value = updatedSettlements
    }
}

