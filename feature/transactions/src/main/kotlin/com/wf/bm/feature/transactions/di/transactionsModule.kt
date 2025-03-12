package com.wf.bm.feature.transactions.di

import com.wf.bm.feature.transactions.create.CreateTransactionViewModel
import com.wf.bm.feature.transactions.create.split.friends_selection.FriendsSelectionDialogViewModel
import com.wf.bm.feature.transactions.details.TransactionDetailsViewModel
import com.wf.bm.feature.transactions.edit.EditTransactionViewModel
import com.wf.bm.feature.transactions.main.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val transactionsModule = module {
    viewModelOf(::CreateTransactionViewModel)
    viewModelOf(::TransactionDetailsViewModel)
    viewModelOf(::EditTransactionViewModel)
    viewModelOf(::TransactionsViewModel)
    viewModelOf(::FriendsSelectionDialogViewModel)
}