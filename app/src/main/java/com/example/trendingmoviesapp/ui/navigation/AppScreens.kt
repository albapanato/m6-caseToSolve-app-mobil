package com.example.trendingmoviesapp.ui.navigation

/**
 * Definición de pantallas de la aplicación para un mejor control de la navegación.
 */
sealed class AppScreens(val route: String) {
    data object ApiKeyScreen : AppScreens("api_key_screen")
    data object MainScreen : AppScreens("main_screen")
    data object DetailScreen : AppScreens("detail_screen")
}