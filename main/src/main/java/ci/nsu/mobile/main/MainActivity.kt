package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels // Важно: импорт делегата для ViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ci.nsu.mobile.main.ui.MainApp
import ci.nsu.mobile.main.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {

    // Инициализируем ViewModel на уровне Активити.
    // Она автоматически сохраняет своё состояние при изменении конфигурации (например, повороте экрана).
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(viewModel = authViewModel)
                }
            }
        }
    }
}