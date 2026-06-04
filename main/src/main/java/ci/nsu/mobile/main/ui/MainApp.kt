package ci.nsu.mobile.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ci.nsu.mobile.main.viewmodel.AuthViewModel

@Composable
fun MainApp(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        when (currentScreen) {
            Screen.LOGIN -> LoginScreen(viewModel = viewModel)
            Screen.REGISTER -> RegisterScreen(viewModel = viewModel)
            Screen.MAIN -> MainScreen(viewModel = viewModel)
        }
    }
}