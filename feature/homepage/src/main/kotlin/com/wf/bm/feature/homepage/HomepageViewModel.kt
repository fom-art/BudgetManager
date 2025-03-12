package com.wf.bm.feature.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wf.bm.core.data.util.AuthenticationManager
import com.wf.bm.core.model.Currency
import com.wf.bm.core.model.Goal
import com.wf.bm.core.model.Transaction
import com.wf.bm.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomepageViewModel(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {
    private val _homepageState = MutableStateFlow(HomepageState())
    var state = _homepageState.asStateFlow()

    init {
        observeCurrentUser()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            authenticationManager.observeCurrentUser { user ->
                _homepageState.update {
                    it.copy(user = user)
                }
            }
        }
    }

    data class HomepageState(
        val user: User? = null,
        val preferredCurrency: Currency = Currency.USD,
    ) {
        val goals: List<Goal>
            get() = user?.goals ?: emptyList()
        val expenses: List<Transaction>
            get() = user?.transactions?.filter { !it.isPositive } ?: emptyList()
    }
}