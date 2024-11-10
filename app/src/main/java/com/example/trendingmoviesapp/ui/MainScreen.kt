package com.example.trendingmoviesapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.trendingmoviesapp.model.Movie
import com.example.trendingmoviesapp.theme.TrendingMoviesAppTheme
import com.example.trendingmoviesapp.ui.navigation.AppScreens
import com.example.trendingmoviesapp.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(appViewModel: AppViewModel, navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchInProgress = remember { mutableStateOf(false) }

    // Aquí obtenemos el estado del tema desde el AppViewModel o SharedPreferences
    val isDarkTheme = appViewModel.isDarkTheme.collectAsState(initial = false).value

    TrendingMoviesAppTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(), // Asegura que el Box ocupe todo el ancho
                            contentAlignment = Alignment.CenterEnd // Centra el contenido (el texto)
                        ) {
                            Text(
                                text = "FILMS",
                                color = Color.White, // Establece el color del texto como blanco
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 16.dp)

                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate(AppScreens.ApiKeyScreen.route) }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "black",
                                tint = Color.White ) // Asegúrate de agregar esta propiedad)
                        }
                    },colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF080946)
                ))
            }
        ) { innerPadding ->
            // Box que envuelve el contenido con un fondo dependiendo del tema
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background) // Fondo que cambia según el tema
            ) {
                Column {
                    // Formulario de búsqueda
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    ) {
                        OutlinedTextField(
                            value = appViewModel.query,
                            onValueChange = { newText -> appViewModel.query = newText },
                            label = { Text(text = "Search for movies") },
                            maxLines = 1,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    keyboardController?.hide() // Se oculta el teclado
                                    appViewModel.searchMovie(appViewModel.query, searchInProgress)
                                }
                            ),
                            enabled = !searchInProgress.value
                        )
                        Button(
                            onClick = {
                                keyboardController?.hide()
                                appViewModel.searchMovie(appViewModel.query, searchInProgress)
                            },
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .padding(start = 8.dp),
                            enabled = !searchInProgress.value,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0BB5E2), // Color de fondo del botón
                                contentColor = Color.White // Color del texto/icono dentro del botón
                            )
                        ) {
                            Icon(Icons.Filled.Search, contentDescription = "Search") // Asegúrate de incluir contentDescription por accesibilidad
                        }
                    }

                    // Indicador de búsqueda en progreso
                    if (searchInProgress.value) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .width(64.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                // Mostrar las películas en una cuadrícula con LazyVerticalGrid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Esto crea una cuadrícula con 2 columnas
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp, top = 80.dp) // Espaciado entre las celdas
                ) {
                    itemsIndexed(appViewModel.moviesList) { index, movie ->
                        MovieCard(
                            movie = movie,
                            onClick = {
                                appViewModel.selectedMovieIndex = index
                                navController.navigate(AppScreens.DetailScreen.route)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(8.dp)
            .clickable { onClick() }
            .shadow(4.dp), // Aquí aplicamos la sombra
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de la película (poster)
            Image(
                painter = rememberImagePainter(
                    data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = "Poster de ${movie.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Ajusta la altura de la imagen
                contentScale = ContentScale.Crop
            )

            // Título debajo de la imagen
            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 20.dp),
                color = MaterialTheme.colorScheme.onSurface // Color de texto adaptado al tema
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(AppViewModel(), rememberNavController())
}
