package com.example.trendingmovies.domain.repositories

import androidx.paging.PagingData
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getMovieDetail(movieId: Int): Flow<WebResponse<MovieDetail>>
}