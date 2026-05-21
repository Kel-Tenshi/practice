package ci.nsu.mobile.main.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.app.Activity

@Composable
fun MainScreen(
    onNavigateToCalculate: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Расчёт вкладов", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(48.dp))

        Button(onClick = onNavigateToCalculate, modifier = Modifier.fillMaxWidth()) {
            Text("Рассчитать")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToHistory, modifier = Modifier.fillMaxWidth()) {
            Text("История расчётов")
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = { activity?.finish() }, modifier = Modifier.fillMaxWidth()) {
            Text("Закрыть приложение")
        }
    }
}