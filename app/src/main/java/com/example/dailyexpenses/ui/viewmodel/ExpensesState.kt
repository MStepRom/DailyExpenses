package com.example.dailyexpenses.ui.viewmodel

import com.example.dailyexpenses.data.model.Expense

data class ExpensesState(
    val cards: List<Expense> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)