package com.example.trendingmovies.domain.model

data class MoviesResponse(
    val page: Int,
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)