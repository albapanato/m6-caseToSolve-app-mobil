import com.example.trendingmoviesapp.model.Actor
import com.example.trendingmoviesapp.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val logo_path: String,
    // Agrega otros campos según sea necesario
)