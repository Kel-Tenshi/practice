package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel
import java.util.Locale

@Composable
fun ResultScreen(
    viewModel: DepositViewModel,
    onNavigateHome: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Результат расчёта", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ResultRow(label = "Стартовый взнос:", value = "${viewModel.initialAmount} ₽")
                ResultRow(label = "Срок вклада:", value = "${viewModel.periodMonths} мес.")
                ResultRow(label = "Процентная ставка:", value = viewModel.selectedRate)

                if (viewModel.monthlyTopUp.isNotEmpty()) {
                    ResultRow(label = "Ежемесячное пополнение:", value = "${viewModel.monthlyTopUp} ₽")
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                ResultRow(
                    label = "Начисленные проценты:",
                    value = String.format(Locale.US, "%.2f ₽", viewModel.interestEarned),
                    isBold = true
                )
                ResultRow(
                    label = "Итоговая сумма:",
                    value = String.format(Locale.US, "%.2f ₽", viewModel.finalAmount),
                    isBold = true
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedButton(onClick = {
                viewModel.saveToDatabase()
                onNavigateHome()
            }) {
                Text("Сохранить")
            }

            Button(onClick = onNavigateHome) {
                Text("В начало")
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = if (isBold) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium)
        Text(text = value, style = if (isBold) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge)
    }
}