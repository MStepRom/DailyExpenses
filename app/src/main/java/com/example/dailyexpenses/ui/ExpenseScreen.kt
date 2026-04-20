package com.example.dailyexpenses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyexpenses.data.model.Expense

@Composable
fun ExpenseScreen(
    cards: List<Expense>,
    total: Int,
    onDelete: (String) -> Unit,
    onAddCard: (String, Int) -> Unit,
) {
    // ===== КРОК 1: Локальний стан для input field =====
    var newCardTitle by remember { mutableStateOf("") }
    var newCardPrice by remember { mutableStateOf(0) }

    // ===== КРОК 2: Основна колонка екрану =====
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        // ===== КРОК 3: Заголовок екрану =====
        Text(
            text = "💸 Витрати за день",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Разом: ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8E8E93)
            )

            Text(
                text = "₴$total",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

        // ===== КРОК 4: Рядок для введення нової карти =====
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ===== Input field для введення назви карти =====
            TextField(
                value = newCardTitle,
                onValueChange = { newCardTitle = it },
                placeholder = { Text("Введи назву витрати...") },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color(0xFF6200EA),
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = if (newCardPrice == 0) "" else newCardPrice.toString(),
                onValueChange = {
                    newCardPrice = it.toIntOrNull() ?: 0
                },
                placeholder = { Text("0") },
                modifier = Modifier
                    .width(110.dp)
                    .height(56.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // ===== Кнопка додавання нової карти =====
        Button(
            onClick = {
                onAddCard(newCardTitle, newCardPrice)
                newCardTitle = ""
                newCardPrice = 0
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = newCardTitle.isNotBlank() && newCardPrice > 0
        ) {
            Text(
                text = "+ Додати витрату",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Divider(
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1f)         // Займає залишкове місце
        ) {
            // ===== Ітеруємо по картах цього статусу =====
            items(
                items = cards,
                key = { it.id }     // Унікальний ключ для кожної карти
            ) { card ->
                // ===== Рендеримо карточку =====
                ExpenseCard(
                    card = card,
                    onDelete = onDelete
                )
            }
        }

    }
}