package com.example.trendingmovies.domain.repositories

import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<WebResponse<MoviesResponse>>
    fun getMovieDetail(movieId: Int): Flow<WebResponse<MovieDetail>>
}