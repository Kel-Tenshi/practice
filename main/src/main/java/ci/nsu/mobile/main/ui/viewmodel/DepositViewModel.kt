package ci.nsu.mobile.main.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.main.data.entity.DepositCalculation
import ci.nsu.mobile.main.data.repository.DepositRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DepositViewModel(private val repository: DepositRepository) : ViewModel() {

    // --- СОСТОЯНИЕ ЭТАПА 1 ---
    var initialAmount by mutableStateOf("")
    var periodMonths by mutableStateOf("")

    // Ошибки валидации для Этапа 1
    var initialAmountError by mutableStateOf<String?>(null)
    var periodMonthsError by mutableStateOf<String?>(null)

    // --- СОСТОЯНИЕ ЭТАПА 2 ---
    var selectedRate by mutableStateOf("")
    var monthlyTopUp by mutableStateOf("")

    // Ошибка для Этапа 2 (если срок не указан, а мы зашли сюда)
    var step2Error by mutableStateOf<String?>(null)

    // --- СОСТОЯНИЕ РЕЗУЛЬТАТА ---
    var finalAmount by mutableStateOf(0.0)
    var interestEarned by mutableStateOf(0.0)

    // --- ДЕТАЛЬНЫЙ ПРОСМОТР ИЗ ИСТОРИИ ---
    var selectedCalculation by mutableStateOf<DepositCalculation?>(null)

    // --- ИСТОРИЯ РАСЧЁТОВ ---
    // Превращаем Flow из базы данных в StateFlow для Compose
    val history: StateFlow<List<DepositCalculation>> = repository.allCalculations
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // --- ВАЛИДАЦИЯ ЭТАПА 1 ---
    fun validateStep1(): Boolean {
        var isValid = true

        val amount = initialAmount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            initialAmountError = "Введите корректную сумму больше 0"
            isValid = false
        } else {
            initialAmountError = null
        }

        val months = periodMonths.toIntOrNull()
        if (months == null || months <= 0) {
            periodMonthsError = "Введите корректный срок в месяцах"
            isValid = false
        } else {
            periodMonthsError = null
        }

        return isValid
    }

    // --- ОБНОВЛЕНИЕ ДОСТУПНОЙ СТАВКИ ---
    // Вызывается при переходе на Этап 2
    fun updateAvailableRate() {
        val months = periodMonths.toIntOrNull()
        if (months == null) {
            step2Error = "Срок вклада не указан! Вернитесь на первый шаг."
            selectedRate = ""
            return
        }

        step2Error = null
        // Логика из ТЗ:
        selectedRate = when {
            months < 6 -> "15%"
            months in 6..11 -> "10%"
            else -> "5%" // от 12 месяцев и выше
        }
    }

    // --- РАСЧЁТ ВКЛАДА ---
    fun calculateDeposit() {
        val startAmount = initialAmount.toDoubleOrNull() ?: 0.0
        val months = periodMonths.toIntOrNull() ?: 0
        val ratePercent = selectedRate.replace("%", "").toDoubleOrNull() ?: 0.0
        val topUp = monthlyTopUp.toDoubleOrNull() ?: 0.0

        val monthlyRate = (ratePercent / 100) / 12
        var currentBalance = startAmount

        // Расчёт вклада по месяцам (сложный процент с учётом пополнений)
        for (i in 1..months) {
            currentBalance += currentBalance * monthlyRate
            // Пополнение капает со второго месяца (или в конце каждого месяца)
            if (i < months) {
                currentBalance += topUp
            }
        }

        finalAmount = currentBalance
        // Начисленные проценты = Итог - Стартовый взнос - (Пополнения * количество месяцев пополнения)
        val totalTopUps = topUp * (months - 1).coerceAtLeast(0)
        interestEarned = finalAmount - startAmount - totalTopUps
    }

    // --- СОХРАНЕНИЕ В БД ---
    fun saveToDatabase() {
        viewModelScope.launch {
            val calculation = DepositCalculation(
                initialAmount = initialAmount.toDoubleOrNull() ?: 0.0,
                periodMonths = periodMonths.toIntOrNull() ?: 0,
                interestRate = selectedRate.replace("%", "").toDoubleOrNull() ?: 0.0,
                monthlyTopUp = monthlyTopUp.toDoubleOrNull(),
                finalAmount = finalAmount,
                interestEarned = interestEarned,
                calculationDate = System.currentTimeMillis() // Текущее время
            )
            repository.insert(calculation)
        }
    }

    // --- СБРОС ДАННЫХ ПРИ ВОЗВРАТЕ В НАЧАЛО ---
    fun resetInputs() {
        initialAmount = ""
        periodMonths = ""
        initialAmountError = null
        periodMonthsError = null
        selectedRate = ""
        monthlyTopUp = ""
        finalAmount = 0.0
        interestEarned = 0.0
        selectedCalculation = null
    }
}