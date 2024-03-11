package com.charros_software.proceso_enfermeria.navigation

sealed class AppScreens(val route: String) {
    data object MainScreen: AppScreens("main_screen")
    data object DiagnosticsScreen: AppScreens("diagnostics_screen")
}