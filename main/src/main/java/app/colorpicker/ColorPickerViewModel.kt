package app.colorpicker

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ColorUiState(
    val red: Int = 128,
    val green: Int = 128,
    val blue: Int = 128
) {
    val color: Color get() = Color(red, green, blue)
    val hexCode: String get() = String.format("#%02X%02X%02X", red, green, blue)
}

class ColorPickerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ColorUiState())
    val uiState: StateFlow<ColorUiState> = _uiState.asStateFlow()

    fun onRedChanged(value: Float) { _uiState.update { it.copy(red = value.toInt()) } }
    fun onGreenChanged(value: Float) { _uiState.update { it.copy(green = value.toInt()) } }
    fun onBlueChanged(value: Float) { _uiState.update { it.copy(blue = value.toInt()) } }

    fun generateRandomColor() {
        _uiState.update { ColorUiState((0..255).random(), (0..255).random(), (0..255).random()) }
    }
}