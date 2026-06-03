package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.MenuAnchorType
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step2Screen(
    viewModel: DepositViewModel,
    onCalculate: () -> Unit,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) { viewModel.updateAvailableRate() }

    var expanded by remember { mutableStateOf(false) }
    // Получаем менеджер фокуса
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Этап 2: Дополнительно", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))

        // ... код выпадающего списка остается без изменений ...
        if (viewModel.step2Error != null) {
            Text(viewModel.step2Error!!, color = Color.Red)
        } else {
            Text("Выбор процентной ставки:", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedRate,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Доступная ставка") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true) // Исправленный вариант
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(viewModel.selectedRate) },
                        onClick = {
                            expanded = false
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Ставка определена автоматически на основе срока вклада", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ОБНОВЛЕННОЕ ПОЛЕ ВВОДА:
        OutlinedTextField(
            value = viewModel.monthlyTopUp,
            onValueChange = { viewModel.monthlyTopUp = it },
            label = { Text("Ежемесячное пополнение (необяз.)") },
            singleLine = true, // Поле в одну строку
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done // Кнопка "Готово"
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus() // Закрывает клавиатуру
                }
            ),
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