package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel

@Composable
fun Step1Screen(
    viewModel: DepositViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    // Менеджер фокуса для управления клавиатурой
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Этап 1: Основные параметры", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.initialAmount,
            onValueChange = { viewModel.initialAmount = it },
            label = { Text("Стартовый взнос") },
            isError = viewModel.initialAmountError != null,
            supportingText = { viewModel.initialAmountError?.let { Text(it) } },
            singleLine = true, // Поле в одну строку
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next // Кнопка "Далее" на клавиатуре
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.periodMonths,
            onValueChange = { viewModel.periodMonths = it },
            label = { Text("Срок вклада (месяцев)") },
            isError = viewModel.periodMonthsError != null,
            supportingText = { viewModel.periodMonthsError?.let { Text(it) } },
            singleLine = true, // Поле в одну строку
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done // Кнопка "Готово" на клавиатуре
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Скрывает клавиатуру при нажатии "Готово"
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onBack) { Text("В начало") }
            Button(onClick = { if (viewModel.validateStep1()) onNext() }) { Text("Далее") }
        }
    }
}