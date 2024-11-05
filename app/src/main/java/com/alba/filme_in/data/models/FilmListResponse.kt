package com.alba.filme_in.data

import com.alba.filme_in.data.models.Film
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmListResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val films: List<Film>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
