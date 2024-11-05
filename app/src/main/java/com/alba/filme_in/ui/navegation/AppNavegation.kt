package com.alba.filme_in.ui.navegation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alba.filme_in.ui.screens.FilScreen
import com.alba.filme_in.ui.screens.FilmDetailScreen
import com.alba.filme_in.ui.viewmodels.FilmViewModel

@Composable
fun AppNavigation() {

    val appViewModel = FilmViewModel() // Se crea una instancia de AppViewModel para manejar el estado de la app y compartirlo entre pantallas
    val navController = rememberNavController() // Crea un objeto NavController para controlar la navegación entre las pantallas

    // Contenedor de las diferentes pantallas de la app
    NavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route, // Pantalla inicial
        enterTransition = { fadeIn() }, // Animación de entrada entre las pantallas
        exitTransition = { fadeOut() }, // Animación de salida entre las pantallas
    ) {

        composable(route = AppScreens.MainScreen.route) {
            AppScreens.MainScreen(navController)
        }
        composable(route = AppScreens.FilmScreen.route) {

            FilmScreen(FilmViewModel, navController)
        }

        composable(route = AppScreens.FilmDetailScreen.route) {
            FilmDetailScreen(FilmViewModel, navController)
        }


    }

}
