package com.wf.bm.core.data.util

sealed class UiEvent{
    data object Success : UiEvent()
    data class Error(val message: String) : UiEvent()
    data object Loading : UiEvent()
}