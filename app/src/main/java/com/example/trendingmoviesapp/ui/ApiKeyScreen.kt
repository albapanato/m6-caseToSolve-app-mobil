package com.example.trendingmoviesapp.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trendingmoviesapp.R

import com.example.trendingmoviesapp.theme.TrendingMoviesAppTheme
import com.example.trendingmoviesapp.constants.Preferences
import com.example.trendingmoviesapp.viewmodel.AppViewModel

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.tmdb), // Usa el nombre exacto aquí
        contentDescription = "Logo TMDB",
        modifier = Modifier.size(100.dp) // Ajusta el tamaño según sea necesario
    )
}

@Composable
fun ApiKeyScreen(onApiKeySaved: (String) -> Unit) {
    val context = LocalContext.current // Se obtiene el contexto
    val sharedPreferences = context.getSharedPreferences(
        Preferences.FILE,
        Context.MODE_PRIVATE
    ) // Sirve para almacenar y recuperar datos persistentes en Android

    // Obtener el estado del tema desde el ViewModel
    val appViewModel: AppViewModel = viewModel()
    var apiKey by remember { mutableStateOf("") }

    // Recuperar la API Key almacenada previamente en las preferencias (si existe)
    val savedApiKey = sharedPreferences.getString(Preferences.API_KEY, "")
    savedApiKey?.let {
        apiKey = it // Si existe, asignar el valor a apiKey
    }

    TrendingMoviesAppTheme(darkTheme = appViewModel.isDarkTheme.collectAsState().value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            // Logo en la parte superior, centrado horizontalmente
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            ) {
                Logo()
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.padding(top = 15.dp)
                ) {
                    // Botón para tema claro
                    Button(
                        onClick = {
                            appViewModel.setDarkTheme(false)
                            sharedPreferences.edit()
                                .putBoolean(Preferences.APP_IN_DARK_THEME, false).apply()
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0BB5E2),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Filled.LightMode, null)
                    }
                    // Botón para tema oscuro
                    Button(
                        onClick = {
                            appViewModel.setDarkTheme(true)
                            sharedPreferences.edit()
                                .putBoolean(Preferences.APP_IN_DARK_THEME, true).apply()
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0BB5E2),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Filled.DarkMode, null)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Usar el recurso de strings para el título
                    Text(
                        text = stringResource(id = R.string.app_name), // Cambiado aquí
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de entrada de API Key y botón para guardar
                    OutlinedTextField(
                        value = apiKey,
                        onValueChange = { apiKey = it },
                        label = { Text(stringResource(id = R.string.enter_api_key)) } // Cambiado aquí
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onApiKeySaved(apiKey)
                            appViewModel.setApiKey(apiKey)
                            sharedPreferences.edit()
                                .putString(Preferences.API_KEY, apiKey).apply()
                        },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0BB5E2),
                            contentColor = Color.White
                        )
                    ) {
                        // Usar el recurso de strings para el texto del botón
                        Text(stringResource(id = R.string.save)) // Cambiado aquí
                    }
                }
            }
        }
    }
}
