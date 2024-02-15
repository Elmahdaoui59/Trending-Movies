package com.example.trendingmovies.data.remote

import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.domain.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.MOVIES_API_KEY
    ): MoviesResponse
}