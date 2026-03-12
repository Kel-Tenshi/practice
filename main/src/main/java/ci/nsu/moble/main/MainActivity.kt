package ci.nsu.moble.main

import android.os.Bundle
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
    // Состояние для текста в поле ввода
    var textFieldValue by remember { mutableStateOf("") }

    // Состояние для цвета основной кнопки (по умолчанию серый или зеленый, как на скрине)
    var buttonColor by remember { mutableStateOf(Color.Green) }

    // Карта соответствия строк цветам
    val colorMap = mapOf(
        "Red" to Color.Red,
        "Orange" to Color(255,50,0),
        "Yellow" to Color.Yellow,
        "Green" to Color.Green,
        "Blue" to Color.Blue,
        "Indigo" to Color(84, 6, 209),
        "Violet" to Color(126, 0, 199)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Поле ввода
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text("Введите цвет (например, Red)") },
            modifier = Modifier.fillMaxWidth()
        )

        // Кнопка "Применить цвет"
        Button(
            onClick = {
                // Ищем цвет в мапе. Если не нашли — оставляем текущий
                val newColor = colorMap[textFieldValue.trim()]
                if (newColor != null) {
                    buttonColor = newColor
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Применить цвет", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список цветных плашек как на макете
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
            .height(60.dp),
        color = color,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Text(
                text = name,
                modifier = Modifier.padding(start = 16.dp),
                color = if (color == Color.Yellow || color == Color.White) Color.Black else Color.White
            )
        }
    }
}