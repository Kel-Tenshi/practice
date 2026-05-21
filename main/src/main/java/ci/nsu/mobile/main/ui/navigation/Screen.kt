package ci.nsu.mobile.main.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Step1 : Screen("step1")
    object Step2 : Screen("step2")
    object Result : Screen("result")
    object History : Screen("history")
}