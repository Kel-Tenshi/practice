package ci.nsu.moble.main

import android.os.Bundle
import android.util.Log // Импорт для логирования
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ci.nsu.moble.main.ui.theme.PracticeTheme

// Тэг для фильтрации в Logcat
private const val TAG = "ColorPicker"

// Структура данных вынесена наружу для стабильности
private val colorMap = mapOf(
    "Red" to Color.Red,
    "Orange" to Color(255, 50, 0),
    "Yellow" to Color.Yellow,
    "Green" to Color.Green,
    "Blue" to Color.Blue,
    "Indigo" to Color(84, 6, 209),
    "Violet" to Color(126, 0, 199)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorPickerScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ColorPickerScreen(modifier: Modifier = Modifier) {
    var textFieldValue by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Green) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Введите название цвета") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val input = textFieldValue.trim()
                val newColor = colorMap[input]

                if (newColor != null) {
                    // Если цвет найден — меняем фон кнопки
                    buttonColor = newColor
                } else {
                    // Если не найден — пишем в Logcat (Вкладка Logcat в Android Studio)
                    Log.e(TAG, "Пользовательский цвет \"$input\" не найден")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Применить цвет", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Доступная палитра:", style = MaterialTheme.typography.titleMedium)

        // Вывод списка с палитрой (Дополнительное задание)
        colorMap.forEach { (name, color) ->
            ColorBlock(name = name, color = color)
        }
    }
}

@Composable
fun ColorBlock(name: String, color: Color) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        color = color,
        shape = MaterialTheme.shapes.small,
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = name,
                modifier = Modifier.padding(start = 16.dp),
                color = if (color == Color.Yellow) Color.Black else Color.White
            )
        }
    }
}