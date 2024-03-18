package com.charros_software.proceso_enfermeria.navigation

sealed class AppScreens(val route: String) {
    data object MainScreen: AppScreens("main_screen")
    data object DiagnosticsScreen: AppScreens("diagnostics_screen")
    data object InterventionsScreen: AppScreens("interventions_screen")
    data object ResultsScreen: AppScreens("results_screen")
    data object CollectionsScreen: AppScreens("collections_screen")
    data object CollectionViewScreen: AppScreens("collection_view_screen")
}