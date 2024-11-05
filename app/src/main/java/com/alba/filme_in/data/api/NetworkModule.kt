package com.alba.filme_in.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val json = Json { ignoreUnknownKeys = true }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiOGNjODdmOTM5OGExODNiZDVjM2JlNDE3MzBiZDkzYSIsIm5iZiI6MTczMDIxMjQxMS4xNTI5Mywic3ViIjoiNjcyMGMwNmY0YmUxNTQ2OWU3MGU2ZDRjIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.2tb1IcwTBn2Ad9Q7uMcPIPdUOUmxpcZOkC4qYl0DmPw")
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: TMDbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TMDbApiService::class.java)
    }
}

