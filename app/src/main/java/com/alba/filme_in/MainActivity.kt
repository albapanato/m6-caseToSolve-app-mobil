package com.alba.filme_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.alba.filme_in.ui.screens.WelcomeScreen /// Seguir creando las pantallas
import com.alba.filme_in.ui.screens.SearchScreen
import com.alba.filme_in.ui.screens.FilmDetailScreen
import com.alba.filme_in.ui.theme.FilMeinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilMeinTheme() {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("search") { SearchScreen(navController) }
                    composable("detail/{filmId}") { backStackEntry ->
                        val filmId = backStackEntry.arguments?.getString("filmId")
                        FilmDetailScreen(navController, filmId = filmId?.toInt() ?: 0)
                    }
                }
            }
        }
    }
}
