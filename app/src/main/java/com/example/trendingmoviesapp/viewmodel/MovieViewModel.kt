package com.example.trendingmoviesapp.viewmodel

import MovieResponse
import TmdbApiService
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmoviesapp.model.Movie
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppViewModel : ViewModel() {

    // Query de búsqueda para el servidor
    var query by mutableStateOf("")

    // Estado de la API Key usando StateFlow
    private val _apiKey = MutableStateFlow<String?>(null)
    val apiKey: StateFlow<String?> = _apiKey.asStateFlow()

    // Función para actualizar el valor de la API Key
    fun setApiKey(key: String) {
        Log.d("AppViewModel", "Setting API Key: $key")
        _apiKey.value = key
        fetchMovies() // Llama a fetchMovies cuando se establece una nueva API Key
    }

    // Guardamos el tema oscuro en el ViewModel, cambiando a StateFlow
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
    }

    // Lista de películas que se puede modificar
    var moviesList = mutableStateListOf<Movie>()
        private set

    // Índice de la película seleccionada
    var selectedMovieIndex by mutableStateOf(0)

    // Mensaje de error
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Función para limpiar la lista de películas
    fun clearMoviesList() {
        moviesList.clear()
    }

    // Función para añadir todas las películas a la lista
    fun moviesListAddAll(moviesList: List<Movie>) {
        this.moviesList.addAll(moviesList)
    }

    private val apiService: TmdbApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(TmdbApiService::class.java)

        // Llama a fetchMovies() si ya tienes la API Key
        if (!apiKey.value.isNullOrEmpty()) {
            fetchMovies()
        }
    }

    // Lista de personas populares
    private val popularPersons = mutableListOf<Movie>()

    // Función para obtener personas populares
    fun fetchPopularPersons(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPopularPersons(apiKey) // Asegúrate de pasar el apiKey
                if (response.isSuccessful) {
                    popularPersons.clear()
                    // Asegúrate de que estás accediendo a la lista de personas correctamente
                    val personResponse = response.body()
                    if (personResponse != null) {
                        popularPersons.addAll(personResponse.results) // Cambia 'movies' a 'results'
                    }
                    Log.d("AppViewModel", "Fetched ${popularPersons.size} persons.")
                } else {
                    errorMessage = "Error: ${response.errorBody()?.string()}"
                    Log.e("AppViewModel", errorMessage ?: "Unknown error")
                }
            } catch (e: Exception) {
                errorMessage = "Exception fetching popular persons: ${e.message}"
                Log.e("AppViewModel", errorMessage, e)
            }
        }
    }

    // Función para obtener películas
    fun fetchMovies() {
        Log.d("AppViewModel", "Fetching movies...")
        viewModelScope.launch {
            try {
                // Asegúrate de que apiKey.value no sea nulo o vacío
                val currentApiKey = apiKey.value
                if (currentApiKey.isNullOrEmpty()) {
                    errorMessage = "API Key is missing"
                    Log.e("AppViewModel", errorMessage ?: "Unknown error")
                    return@launch
                }

                val response = apiService.getMovies(currentApiKey) // Asegúrate de que tu API tenga este método
                if (response.isSuccessful) {
                    Log.d("AppViewModel", "Response body: ${response.body()}")
                    clearMoviesList() // Limpia la lista antes de agregar nuevas películas
                    moviesListAddAll(response.body()?.results ?: emptyList())
                    errorMessage = null // Limpia el mensaje de error
                    Log.d("AppViewModel", "Fetched ${moviesList.size} movies.")
                } else {
                    errorMessage = "Error: ${response.errorBody()?.string()}"
                    Log.e("AppViewModel", errorMessage ?: "Unknown error")
                }
            } catch (e: Exception) {
                errorMessage = "Exception fetching movies: ${e.message}"
                Log.e("AppViewModel", errorMessage, e)
            }
        }
    }

    fun searchMovie(searchValue: String, searchInProgress: MutableState<Boolean>) {
        searchInProgress.value = true
        Log.d("AppViewModel", "Searching movies...")
        viewModelScope.launch {
            try {
                // Asegúrate de que apiKey.value no sea nulo o vacío
                val currentApiKey = apiKey.value
                if (currentApiKey.isNullOrEmpty()) {
                    errorMessage = "API Key is missing"
                    Log.e("AppViewModel", errorMessage ?: "Unknown error")
                    return@launch
                }
                val response = apiService.searchMovie(currentApiKey, searchValue)
                if (response.isSuccessful) {
                    Log.d("AppViewModel", "Response body: ${response.body()}")
                    clearMoviesList() // Limpia la lista antes de agregar nuevas películas
                    moviesListAddAll(response.body()?.results ?: emptyList())
                    errorMessage = null // Limpia el mensaje de error
                    Log.d("AppViewModel", "Fetched ${moviesList.size} movies.")
                } else {
                    errorMessage = "Error: ${response.errorBody()?.string()}"
                    Log.e("AppViewModel", errorMessage ?: "Unknown error")
                }

            } catch (e: Exception) {
                errorMessage = "Exception fetching movies: ${e.message}"
                Log.e("AppViewModel", errorMessage, e)
            } finally {
                searchInProgress.value = false
            }

        }

    }
}
