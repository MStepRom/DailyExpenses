package com.example.dailyexpenses.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailyexpenses.data.model.Expense

@Composable
fun ExpenseCard(
    card: Expense,
    onDelete: (String) -> Unit,
) {
        // ===== КРОК 1: Стан для діалогу видалення =====
        var showDialogFor by remember { mutableStateOf(false) }

        // ===== КРОК 2: Card компонент (контейнер карточки) =====
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clickable(
                    onClick = {
                        showDialogFor = true
                    }
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            // ===== КРОК 3: Внутрішній вміст карточки =====
            ExpenseCardContent(
                card = card
            )
        }

        // ===== КРОК 4: Dialog для видалення =====
        if (showDialogFor) {
            DeleteConfirmationDialog(
                cardTitle = card.title,
                onConfirmDelete = {
                    onDelete(card.id)
                    showDialogFor = false
                },
                onDismiss = {
                    showDialogFor = false
                }
            )
        }
}

// ===== ПІДФУНКЦІЯ 1: Вміст карточки (Title + Button/Text) =====
@Composable
private fun ExpenseCardContent(
    card: Expense
) {

    // ===== Кнопки: вирівняні вправо в горизонтальному ряду =====
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CardTitle(
            card = card,
            Modifier
                .weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        CardPrice(
            card = card,
            Modifier
        )
    }

}

// ===== ПІДФУНКЦІЯ 2: Заголовок карточки (з умовним перекресленням) =====
@Composable
private fun CardTitle(card: Expense, modifier: Modifier = Modifier) {

    Text(
        text = card.title,
        fontSize = 16.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )

}
@Composable
private fun CardPrice(card: Expense, modifier: Modifier = Modifier) {
    Text(
        text = "₴${card.price}",
        color = Color.Red,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

// ===== ПІДФУНКЦІЯ 5: Dialog для видалення =====
@Composable
private fun DeleteConfirmationDialog(
    cardTitle: String,
    onConfirmDelete: () -> Unit,
    onDismiss: () -> Unit,
) {
    // ===== Dialog з підтвердженням видалення карточки =====
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Видалити витрату?") },
        text = { Text("\"$cardTitle\" буде видалено") },
        confirmButton = {
            // ===== Кнопка підтвердження (червона) =====
            Button(
                onClick = onConfirmDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text("Видалити", color = Color.White)
            }
        },
        dismissButton = {
            // ===== Кнопка скасування (сіра) =====
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text("Скасувати", color = Color.White)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ExpenseCardPreview() {
    ExpenseCard(
        card = Expense(
            id = "1",
            title = "Кава з круасаном",
            price = 100
        ),
        onDelete = { /* Preview */ }
    )
}