package com.alba.filme_in.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val page: Int,
    val results: List<Actor>
)

@Serializable
data class Actor(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?,
    val known_for: List<Film>
)

@Serializable
data class Film(
    val backdrop_path: String?,
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val poster_path: String?,
    val media_type: String,
    val adult: Boolean,
    val original_language: String,
    val genre_ids: List<Int>,
    val popularity: Double,
    val release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
