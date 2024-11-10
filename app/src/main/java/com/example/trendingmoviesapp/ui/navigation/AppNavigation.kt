package com.example.trendingmoviesapp.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trendingmoviesapp.theme.TrendingMoviesAppTheme
import com.example.trendingmoviesapp.ui.ApiKeyScreen
import com.example.trendingmoviesapp.ui.MainScreen
import com.example.trendingmoviesapp.ui.DetailScreen
import com.example.trendingmoviesapp.viewmodel.AppViewModel
//import com.example.trendingmoviesapp.viewmodel.PreviewAppViewModel

@Composable
fun AppNavigation(onApiKeySaved: (String) -> Unit) {
    // Obtener el ViewModel de la app
    val appViewModel: AppViewModel = viewModel()
    val apiKey by appViewModel.apiKey.collectAsState()

    // Controlador de navegación
    val navController = rememberNavController()

    // Contenedor de las diferentes pantallas de la app
    // Contenedor de las diferentes pantallas de la app
    NavHost(
        navController = navController,
        startDestination = if (apiKey.isNullOrEmpty()) AppScreens.ApiKeyScreen.route else AppScreens.MainScreen.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
    ) {
        // Pantalla de ingreso de API Key
        composable(route = AppScreens.ApiKeyScreen.route) {
            ApiKeyScreen(onApiKeySaved = { key ->
                appViewModel.setApiKey(key)
                onApiKeySaved(key) // Guardar en SharedPreferences desde MainActivity
                navController.navigate(AppScreens.MainScreen.route) {
                    popUpTo(AppScreens.ApiKeyScreen.route) { inclusive = true }
                }
            })
        }

        // Pantalla principal donde se listan las películas
        composable(route = AppScreens.MainScreen.route) {
            TrendingMoviesAppTheme(darkTheme = appViewModel.isDarkTheme.value) {
                MainScreen(appViewModel = appViewModel, navController = navController)
            }
        }

        // Pantalla de detalles de una película
        composable(route = AppScreens.DetailScreen.route) {
            TrendingMoviesAppTheme(darkTheme = appViewModel.isDarkTheme.value) {
                DetailScreen(appViewModel = appViewModel, navController = navController)
            }
        }
    }
}