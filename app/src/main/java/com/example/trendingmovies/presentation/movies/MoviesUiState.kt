package com.example.trendingmovies.presentation.movies

import com.example.trendingmovies.domain.model.Movie

data class MoviesUiState(
    val isLoading: Boolean = false,
    val errorId: Int? = null,
    val movies: List<Movie>? = emptyList()
)
