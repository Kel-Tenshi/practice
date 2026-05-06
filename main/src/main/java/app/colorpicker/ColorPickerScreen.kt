package app.colorpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ColorPickerScreen(vm: ColorPickerViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Квадрат с цветом
        Box(modifier = Modifier.size(100.dp).background(state.color))

        Text(text = state.hexCode, style = MaterialTheme.typography.headlineMedium)

        // Слайдеры
        MySlider("R", state.red.toFloat()) { vm.onRedChanged(it) }
        MySlider("G", state.green.toFloat()) { vm.onGreenChanged(it) }
        MySlider("B", state.blue.toFloat()) { vm.onBlueChanged(it) }

        Button(onClick = { vm.generateRandomColor() }) {
            Text("Случайный цвет")
        }
    }
}

@Composable
fun MySlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.width(20.dp))
        Slider(value = value, onValueChange = onValueChange, valueRange = 0f..255f)
    }
}