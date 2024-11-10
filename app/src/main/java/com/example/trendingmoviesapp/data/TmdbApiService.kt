import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES" // Por defecto en español
    ): Response<MovieResponse>

    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") searchValue: String,
        @Query("language") language: String = "es-ES" // Por defecto en español
    ): Response<MovieResponse>
}