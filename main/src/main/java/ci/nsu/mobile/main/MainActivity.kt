package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ci.nsu.mobile.main.data.database.AppDatabase
import ci.nsu.mobile.main.data.repository.DepositRepository
import ci.nsu.mobile.main.ui.navigation.NavGraph
import ci.nsu.mobile.main.ui.theme.PracticeTheme
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel
import ci.nsu.mobile.main.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    // Инициализируем базу данных, репозиторий и ViewModel через Фабрику
    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { DepositRepository(database.depositDao()) }

    private val viewModel: DepositViewModel by viewModels {
        ViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Оборачиваем всё в тему Material 3 вашего проекта
            PracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Создаем контроллер навигации Compose
                    val navController = rememberNavController()

                    // Запускаем граф навигации, передавая контроллер и общую ViewModel
                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}