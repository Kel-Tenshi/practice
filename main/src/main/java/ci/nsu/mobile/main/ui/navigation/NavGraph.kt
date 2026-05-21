package ci.nsu.mobile.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ci.nsu.mobile.main.ui.screens.*
import ci.nsu.mobile.main.ui.viewmodel.DepositViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: DepositViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToCalculate = {
                    viewModel.resetInputs() // Очищаем старые данные перед новым расчётом
                    navController.navigate(Screen.Step1.route)
                },
                onNavigateToHistory = { navController.navigate(Screen.History.route) }
            )
        }

        composable(Screen.Step1.route) {
            Step1Screen(
                viewModel = viewModel,
                onNext = { navController.navigate(Screen.Step2.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Step2.route) {
            Step2Screen(
                viewModel = viewModel,
                onCalculate = { navController.navigate(Screen.Result.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Result.route) {
            ResultScreen(
                viewModel = viewModel,
                onNavigateHome = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}