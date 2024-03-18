package com.charros_software.proceso_enfermeria.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.charros_software.proceso_enfermeria.ui.screens.CollectionViewScreen
import com.charros_software.proceso_enfermeria.ui.screens.CollectionsScreen
import com.charros_software.proceso_enfermeria.ui.screens.DiagnosticsScreen
import com.charros_software.proceso_enfermeria.ui.screens.InterventionsScreen
import com.charros_software.proceso_enfermeria.ui.screens.MainScreen
import com.charros_software.proceso_enfermeria.ui.screens.ResultsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.MainScreen.route) {
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = AppScreens.DiagnosticsScreen.route) {
            DiagnosticsScreen(navController = navController)
        }
        composable(route = AppScreens.InterventionsScreen.route) {
            InterventionsScreen(navController = navController)
        }
        composable(route = AppScreens.ResultsScreen.route) {
            ResultsScreen(navController = navController)
        }
        composable(route = AppScreens.CollectionsScreen.route) {
            CollectionsScreen(navController = navController)
        }
        composable(route = AppScreens.CollectionViewScreen.route + "/{idCollection}",
            arguments = listOf(navArgument(name = "idCollection") {
                type = NavType.StringType
            })) {
            CollectionViewScreen(navController = navController, collectionId = it.arguments?.getString("idCollection"))
        }
    }
}