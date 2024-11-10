package com.example.trendingmoviesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.trendingmoviesapp.model.Movie
import com.example.trendingmoviesapp.viewmodel.AppViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(appViewModel: AppViewModel, navController: NavController) {

    val movie =
        appViewModel.moviesList[appViewModel.selectedMovieIndex] // Obtener la película desde el ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                //title = { Text("Detail Screen") },
                { Text(movie.title, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF080946) // fondo de la barra superior
                ),

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack, contentDescription = "black",
                            tint = Color.White
                        ) // Asegúrate de agregar esta propiedad)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF080946)) // Fondo de la página
        ) {
            // Imagen de fondo o póster
            movie.backdrop_path?.let { path ->
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/$path",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(300.dp)
                        .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                        .shadow(
                            8.dp,
                            RoundedCornerShape(12.dp)
                        ) // Sombra para darle un toque de profundidad
                        .background(Color.Gray)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Calificación de la película con icono
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xB3000000)) // Fondo oscuro detrás del texto
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp)), // Bordes redondeados
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${movie.vote_average}/10",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFf3f3f8)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Descripción de la película
            Text(
                text = "Description: ${movie.overview}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFf3f3f8),
                lineHeight = 24.sp, // Mayor altura entre líneas para mejorar la legibilidad
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xB3000000)) // Fondo oscuro detrás del texto para mejor contraste
                    .padding(12.dp) // Padding interno para alejar el texto del borde
                    .clip(RoundedCornerShape(8.dp)),
                textAlign = TextAlign.Justify// Bordes redondeados en la caja de texto
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha de lanzamiento
            Text(
                text = "Release Date:${movie.release_date}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFf3f3f8),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .background(Color(0xB3000000)) // Fondo oscuro detrás del texto
                    .padding(12.dp) // Padding interno
                    .clip(RoundedCornerShape(8.dp)) // Bordes redondeados
            )

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(AppViewModel(), rememberNavController())
}
