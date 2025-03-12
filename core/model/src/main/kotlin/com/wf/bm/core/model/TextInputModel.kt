package com.wf.bm.core.model

data class TextInputModel(
    val value: String,
    val errorMessage: String?,
    val onChangeValue: (String) -> Unit,
)
