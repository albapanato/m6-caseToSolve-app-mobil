package com.alba.filme_in.data.api

import com.alba.filme_in.data.repository.FilmRepository
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): FilmRepository
}
