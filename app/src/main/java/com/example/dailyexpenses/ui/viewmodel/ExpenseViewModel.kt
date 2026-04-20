package com.example.dailyexpenses.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dailyexpenses.data.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted

class ExpenseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ExpensesState())
    val uiState: StateFlow<ExpensesState> = _uiState.asStateFlow()

    val totalPrice: StateFlow<Int> = uiState
        .map { state -> state.cards.sumOf { it.price } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    fun deleteCard(cardId: String) {
        // ===== Беремо поточний список карт =====
        val currentCards = _uiState.value.cards

        // ===== Фільтруємо список: залишаємо всі карти крім видаленої =====
        val updatedCards = currentCards.filter { it.id != cardId }

        // ===== Оновлюємо StateFlow новим списком =====
        _uiState.value = _uiState.value.copy(cards = updatedCards)
    }

    // ===== КРОК 7: Додавання нової карти =====
    fun addNewCard(title: String, price: Int) {
        // ===== Валідація: не додаємо порожні карти =====
        if (title.isBlank() || price == 0) return

        // ===== Беремо поточний список карт =====
        val currentCards = _uiState.value.cards

        // ===== Генеруємо унікальний ID: max(існуючих) + 1 =====
        // Приклад: якщо є ID "1", "2", "4", то новий ID буде "5"
        val newId = (currentCards.maxOfOrNull { it.id.toLongOrNull() ?: 0 } ?: 0) + 1

        // ===== Створюємо нову карту =====
        val newCard = Expense(
            id = newId.toString(),
            title = title.trim(),  // Видаляємо пробіли на початку/кінці
            price = price
        )

        // ===== Додаємо нову карту в список (+ оператор) =====
        val updatedCards = currentCards + newCard

        // ===== Оновлюємо StateFlow новим списком =====
        _uiState.value = _uiState.value.copy(cards = updatedCards)
    }
}