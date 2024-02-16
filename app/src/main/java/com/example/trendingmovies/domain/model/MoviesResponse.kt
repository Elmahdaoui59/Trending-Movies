package com.example.trendingmovies.domain.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)