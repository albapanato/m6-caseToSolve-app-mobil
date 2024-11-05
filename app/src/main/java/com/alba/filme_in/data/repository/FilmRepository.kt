package com.alba.filme_in.data.repository

import com.alba.filme_in.data.FilmListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmService {
    @GET("person/popular")
    suspend fun getPopularFilms(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<FilmListResponse>
}

class FilmRepository(private val filmService: FilmService) {
    suspend fun fetchPopularFilms(page: Int = 1): Response<FilmListResponse> {
        return filmService.getPopularFilms(page = page)
    }
}
