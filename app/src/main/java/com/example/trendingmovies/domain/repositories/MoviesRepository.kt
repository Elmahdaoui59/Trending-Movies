package com.example.trendingmovies.domain.repositories

import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<WebResponse<MoviesResponse>>
}