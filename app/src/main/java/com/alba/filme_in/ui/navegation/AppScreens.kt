package com.alba.filme_in.ui.navegation

sealed class AppScreens(val route: String) {

        data object MainScreen : AppScreens("welcome_screen")
        data object FilmScreen : AppScreens("main_screen")
        data object FilmDetailScreen : AppScreens("detail_screen")
}
