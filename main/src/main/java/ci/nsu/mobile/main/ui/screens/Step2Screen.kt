package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel

@Composable
fun Step2Screen(
    viewModel: DepositViewModel,
    onCalculate: () -> Unit,
    onBack: () -> Unit
) {
    // При входе на экран обновляем ставку
    LaunchedEffect(Unit) { viewModel.updateAvailableRate() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Этап 2: Дополнительно", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.step2Error != null) {
            Text(viewModel.step2Error!!, color = Color.Red)
        } else {
            Text("Ваша процентная ставка: ${viewModel.selectedRate}", style = MaterialTheme.typography.bodyLarge)
            Text("Определена автоматически на основе срока", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.monthlyTopUp,
            onValueChange = { viewModel.monthlyTopUp = it },
            label = { Text("Ежемесячное пополнение (необяз.)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack) { Text("Назад") }
            Button(
                enabled = viewModel.step2Error == null,
                onClick = {
                    viewModel.calculateDeposit()
                    onCalculate()
                }
            ) { Text("Рассчитать") }
        }
    }
}