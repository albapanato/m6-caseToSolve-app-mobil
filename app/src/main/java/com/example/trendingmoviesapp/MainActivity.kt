package com.example.trendingmoviesapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trendingmoviesapp.theme.TrendingMoviesAppTheme
import com.example.trendingmoviesapp.ui.navigation.AppNavigation
import com.example.trendingmoviesapp.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Activar borde a borde si es necesario

        setContent {
            TrendingMoviesAppTheme {
                val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val savedApiKey = sharedPreferences.getString("API_KEY", null)

                // Obtener el ViewModel de la app
                val appViewModel: AppViewModel = viewModel()

                // Verificar si existe la API Key guardada y actualizar en el ViewModel
                LaunchedEffect(Unit) {
                    savedApiKey?.let { appViewModel.setApiKey(it) }
                }

                // Pasar `onApiKeySaved` a AppNavigation
                AppNavigation(onApiKeySaved = { key ->
                    with(sharedPreferences.edit()) {
                        putString("API_KEY", key)
                        apply()
                    }
                    appViewModel.setApiKey(key)  // Actualizar en el ViewModel también
                })
            }
        }
    }
}
