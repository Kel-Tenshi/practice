package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.data.entity.DepositCalculation
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: DepositViewModel,
    onBack: () -> Unit
) {
    // Подписываемся на StateFlow из нашей ViewModel
    val historyList by viewModel.history.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("История расчётов", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (historyList.isEmpty()) {
            Box(modifier = Modifier.weight(1f)) {
                Text("История пока пуста", modifier = Modifier.padding(vertical = 16.dp))
            }
        } else {
            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(historyList) { item ->
                    HistoryItem(calculation = item) {
                        viewModel.selectedCalculation = item
                    }
                }
            }
        }

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Назад на главную")
        }
    }

    // Диалоговое окно для детального просмотра
    viewModel.selectedCalculation?.let { calc ->
        AlertDialog(
            onDismissRequest = { viewModel.selectedCalculation = null },
            confirmButton = {
                TextButton(onClick = { viewModel.selectedCalculation = null }) { Text("ОК") }
            },
            title = { Text("Детальная информация") },
            text = {
                Column {
                    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                    Text("Дата: ${sdf.format(Date(calc.calculationDate))}")
                    Text("Стартовый взнос: ${calc.initialAmount} ₽")
                    Text("Срок вклада: ${calc.periodMonths} мес.")
                    Text("Ставка: ${calc.interestRate}%")
                    calc.monthlyTopUp?.let { Text("Пополнение: $it ₽") }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text("Начисленные проценты: ${String.format(Locale.US, "%.2f", calc.interestEarned)} ₽")
                    Text("Итоговая сумма: ${String.format(Locale.US, "%.2f", calc.finalAmount)} ₽")
                }
            }
        )
    }
}

@Composable
fun HistoryItem(calculation: DepositCalculation, onClick: () -> Unit) {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val dateStr = sdf.format(Date(calculation.calculationDate))

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(dateStr, style = MaterialTheme.typography.bodySmall)
                Text("${calculation.interestRate}%", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Взнос: ${calculation.initialAmount} ₽", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Итог: ${String.format(Locale.US, "%.2f", calculation.finalAmount)} ₽",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}